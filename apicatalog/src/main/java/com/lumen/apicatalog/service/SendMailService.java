package com.lumen.apicatalog.service;

import java.io.File;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class SendMailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendMailWithAttachment(String to, String subject, String body, String fileToAttach) 
	{
	    MimeMessagePreparator preparator = new MimeMessagePreparator() 
	    {
	    	@Override
	        public void prepare(MimeMessage mimeMessage) throws Exception 
	        {
	    		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	    		helper.addTo(new InternetAddress(to));
	    		
	    		helper.setFrom(new InternetAddress("api-support@centurylink.com"));
	    		helper.setSubject(subject);
	    		helper.setText(body);
	             
	            FileSystemResource file = new FileSystemResource(new File(fileToAttach));
	         
	            helper.addAttachment(fileToAttach, file);
	        }
	    };
	     
	    try {
	        mailSender.send(preparator);
	    }
	    catch (MailException ex) {
	        // simply log it and go on...
	        ex.printStackTrace();
	    }
	}
	
	public void sendMail(String to, String subject, String body) 
	{
	    MimeMessagePreparator preparator = new MimeMessagePreparator() 
	    {
	    	@Override
	        public void prepare(MimeMessage mimeMessage) throws Exception 
	        {
	    		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
	    		helper.addTo(new InternetAddress(to));
	    		
	    		helper.setFrom(new InternetAddress("api-support@centurylink.com"));
	    		helper.setSubject(subject);
	    		helper.setText(body);
          

	        }
	    };
	     
	    try {
	        mailSender.send(preparator);
	    }
	    catch (MailException ex) {
	        // simply log it and go on...
	        ex.printStackTrace();
	    }
	}
}
