package com.lumen.apicatalog.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lumen.apicatalog.service.CatalogInfoSvc;
import com.lumen.apicatalog.constants.Constants;
import com.lumen.apicatalog.dao.EmailRequest;
import com.lumen.apicatalog.model.ApiCatalogInfo;



@Component
public class NotificationScheduler {
	private static final Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);
	
	@Autowired
	CatalogInfoSvc catalogInfoSvc;
	
	@Autowired
	JmsTemplate jmsTemplate;
	@Scheduled(cron = "0 0 1 * * MON")
	public void scheduledNotification() {

		logger.info("Running scheduledNotification()");
		List<EmailRequest> notifyReqList = new ArrayList<EmailRequest>();
		List<ApiCatalogInfo> notifyApiList = new ArrayList<ApiCatalogInfo>();
		
		notifyApiList=catalogInfoSvc.getAPIInfo().stream().filter(api->isBlank(api.getApiSwagUrl())||api.getApiModels().isEmpty()).collect(Collectors.toList());
		notifyApiList.stream().forEach(apiInfo->{
			EmailRequest req= new EmailRequest();
			req.setApiName(apiInfo.getApiName());
			req.setEmailAddr(apiInfo.getUserProfile().getEmailAddress());
			req.setSwagger(isBlank(apiInfo.getApiSwagUrl())?"false":"true");
			req.setModel(apiInfo.getApiModels().isEmpty()?"false":"true");
			notifyReqList.add(req);
		});
		
		notifyReqList.stream().forEach(req->{
			if (!isBlank(req.getEmailAddr()) )
				jmsTemplate.convertAndSend(Constants.MESSAGE_DESTINATION_NAME, req);
		});
		
		logger.info("Done scheduledNotification()");
	}
	private boolean isBlank(String apiSwagUrl) {
		// TODO Auto-generated method stub
		if (apiSwagUrl==null)
			return true;
		if (apiSwagUrl.isEmpty())
			return true;
		
		return false;
	}

}
