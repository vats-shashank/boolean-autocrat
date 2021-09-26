package com.lumen.apicatalog.jms;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.lumen.apicatalog.constants.Constants;
import com.lumen.apicatalog.dao.EmailRequest;
import com.lumen.apicatalog.service.SendMailService;





@Component
public class MessageListener {
	private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
	
	@Autowired
	SendMailService emailSvc;
	
	@JmsListener(destination = Constants.MESSAGE_DESTINATION_NAME, containerFactory = "apiCatalogFactory")
	public void receiveMessage(EmailRequest request) {
		log.info("Received <" + request.toString() + ">");
		try {
			String body="";
			if(!request.getModel().equalsIgnoreCase("true") && !request.getSwagger().equalsIgnoreCase("true")) {
				body = "Please add "+request.getApiName()+" API missing details as below. \n\n 1. Swagger URL\n 2. Request/Response models \n\n Thanks\n API Catalog Support" ;
			
			}
			else if (!request.getSwagger().equalsIgnoreCase("true")){
				body = "Please add "+request.getApiName()+" API missing details as below. \n\n 1. Swagger URL\n \n Thanks\n API Catalog Support" ;
			}
			else if (!request.getModel().equalsIgnoreCase("true")) {
				body = "Please add "+request.getApiName()+" API missing details as below. \n\n 1. Request/Response models \n \n Thanks\n API Catalog Support" ;
			}
			
			emailSvc.sendMail(request.getEmailAddr(), request.getApiName()+" API - Missing details", body);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
