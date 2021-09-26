package com.lumen.apicatalog.service;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lumen.apicatalog.exception.BusinessException;
import com.lumen.apicatalog.model.UserProfile;
import com.lumen.apicatalog.repository.UserProfileRepository;
import com.qwest.MnetLDAP.MnetLDAPService;

import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;

/**
 * 
 * @author Pooja
 *
 */
@Service
public class LdapService {
	private static final Logger logger = LoggerFactory.getLogger(LdapService.class);

	@Value("${ldap.host}") String ldapHost;
	@Value("${ldap.port}") int ldapPort;
	@Value("${ldap.sslport}") int ldapSslPOrt;
	@Value("${ldap.baseDN}") String baseDn;
	@Value("${ldap.minNumConnection}") String minConn;
	@Value("${ldap.maxNumConnection}") String maxConn;
	@Value("${ldap.appCUID}") String appCUID;
	@Value("${ldap.appSpring}") String appPasswd;
	@Value("${ldap.LdapGroupDN}") String appDN;
	
	private static MnetLDAPService ldapSvc = null;
	
	@Autowired
	private UserProfileRepository userProfileRepository;

	public UserProfile getSetUserProfile(String cuid) {
		UserProfile userProfile = null;
		logger.info("Start getSetUserProfile()");
		if(cuid!=null) {
			userProfile = userProfileRepository.getByCUID(cuid);
			if(userProfile!=null) {
				logger.info("End getSetUserProfile");
				return userProfile;
			}else {
				userProfile = getUserFromLDAP(cuid);
			}
		}
		logger.info("End getSetUserProfile");
		
		logger.info(userProfile.toString());
		return userProfile;
	}

	public UserProfile getUserFromLDAP(String cuid) {
		UserProfile userProfile = null;
		String firstName = "";
		String lastName = "";
		String mail = "";
		String managerCuid = "";
		
		try {
			setup(cuid);
			
			logger.info(cuid);
			logger.info(""+MnetLDAPService.ALL_ATTRS);
			logger.info(""+ldapSvc);
			
			LDAPEntry userEntry = ldapSvc.getUser(cuid,MnetLDAPService.ALL_ATTRS, null);
			if (userEntry == null) {
				logger.error("Non-existent user CUID: ");
				logger.info("End getUserFromLDAP");
				return null;
			}

			LDAPAttribute attr = userEntry.getAttribute("mail");
			
			Enumeration<String> attrEnum = null;
			
			if(attr != null)
			{
				attrEnum = (Enumeration<String>) attr.getStringValues();
				if (attrEnum!= null && attrEnum.hasMoreElements()) {
					mail = (String) attrEnum.nextElement();
				}
			}
					
			attr = userEntry.getAttribute("givenname");
			
			if(attr != null)
			{
				attrEnum = (Enumeration<String>) attr.getStringValues();
				if (attrEnum!= null && attrEnum.hasMoreElements()) {
					firstName = (String) attrEnum.nextElement();
				}

			}
			
			attr = userEntry.getAttribute("sn");
			if(attr != null)
			{
				attrEnum = (Enumeration<String>) attr.getStringValues();
				if (attrEnum!= null && attrEnum.hasMoreElements()) {
					lastName = (String) attrEnum.nextElement();
				}
			}
			
			attr = userEntry.getAttribute("manager");
			if(attr != null)
			{
				attrEnum = (Enumeration<String>) attr.getStringValues();

				if (attrEnum!= null && attrEnum.hasMoreElements()) {
					String managerCuidLongData = (String) attrEnum.nextElement();

					String[] managerCuidSplit = managerCuidLongData.split(",");
					String managerCuidWithUid = null;
					if (managerCuidSplit != null && managerCuidSplit.length >= 1) {
						managerCuidWithUid = managerCuidSplit[0];
						if (managerCuidWithUid != null) {
							String[] managerCuidArr = managerCuidWithUid.split("=");
							if (managerCuidArr != null
									&& managerCuidArr.length >= 1) {
								managerCuid = managerCuidArr[1];

							}
						}
					}
				}
			}
			userProfile = new UserProfile();
			userProfile.setFirstName(firstName);
			userProfile.setEmailAddress(mail);
			userProfile.setLastName(lastName);
			userProfile.setSupervisorId((managerCuid));
			/*ObjectMapper mapper = new ObjectMapper();
			response = mapper.writeValueAsString(userProfile);*/

		} catch (LDAPException ldEx) {
			logger.error("LDAPException caught in getUserFromLDAP(), throw new BusinessException... "
							+ ldEx.errorCodeToString(), ldEx);
			throw new BusinessException(HttpStatus.BAD_REQUEST, "LDAPException caught, "+ ldEx.errorCodeToString(), ldEx);
		} catch (Exception ex) {
			logger.error("Exception caught in getUserFromLDAP(), throw new BusinessException",
					ex);
			throw new BusinessException(HttpStatus.BAD_REQUEST, "Exception caught, "+ex.getMessage());
		}
		return userProfile;
	}
	
	
	
	/**
	 * Setup MnetLDAPService pool objects
	 * @throws LDAPException
	 */
	public void setup(String cuid) throws Exception {
			try {
				String appDN = null;
				int minConnInt = 1;
				int maxConnInt = 10;
				if (minConn != null && minConn.length() > 0){
					minConnInt = Integer.parseInt(minConn);
				}
				if (maxConn != null && maxConn.length() > 0){
					maxConnInt = Integer.parseInt(maxConn);
				}
								
				ldapSvc = new MnetLDAPService(ldapHost, ldapPort, false, null,null, baseDn, minConnInt, maxConnInt);

				LDAPEntry appEntry = ldapSvc.getUser(appCUID, null, null);
				
				if (appEntry == null) {
					logger.error("Non-existent application CUID: [" + appCUID + "], throw new Exception...");
					throw new Exception("Non-existent application CUID: [" + appCUID + "]");
				}
				appDN = appEntry.getDN();
				logger.info("appDN is: " + appDN);

				// Empty the non-SSL connection pool
				ldapSvc.close();

				ldapSvc = new MnetLDAPService(ldapHost, ldapSslPOrt, true, appDN,appPasswd, baseDn);
				ldapSvc.setDisconnect(false);
				
			} catch (LDAPException ldEx) {
				if (ldapSvc != null){
					ldapSvc.close();
					ldapSvc = null;
				}
				logger.error(
						"LDAPException caught in setup(), throw new Exception... "
								+ ldEx.errorCodeToString(), ldEx);
				throw new Exception("LDAPException caught, "
						+ ldEx.errorCodeToString(), ldEx);
			} catch (Throwable ex) {
				if (ldapSvc != null){
					ldapSvc.close();
					ldapSvc = null;
				}
				logger.error("Throwable caught in setup(), throw new Exception", ex);
				throw new Exception("Throwable caught, ", ex);
				
			}
		}
}
