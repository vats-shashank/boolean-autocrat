package com.lumen.apicatalog.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.lumen.apicatalog.DTO.ResponseDTO;
import com.lumen.apicatalog.constants.Constants;
import com.lumen.apicatalog.dao.EmailRequest;

@Component
public class ThreadProcessor {

	private static final Logger logger = LoggerFactory.getLogger(ThreadProcessor.class);

	private ExecutorService executorService = null;
	private List<ResponseDTO> responseDTO;

	@Autowired
	ApplicationThreadPoolUtil applicationThreadPoolUtil;

	@Autowired
	JmsTemplate jmsTemplate;

	public ThreadProcessor() {
		super();
	}

	public void setThreadProcessorWithResponseDTO(List<ResponseDTO> responseDTOs) {
		this.responseDTO = responseDTOs;
	}

	public void processRequest() {
		executorService = applicationThreadPoolUtil.getExecutorServiceInstance();
		executorService.submit(new ThreadsCreation());
	}

	private class ThreadsCreation implements Runnable {
		@Override
		public void run() {
			List<EmailRequest> notifyReqList = new ArrayList<EmailRequest>();
			List<ResponseDTO> notifyApiList = new ArrayList<ResponseDTO>();
			
			if (responseDTO != null && responseDTO.size() > 0) {
				notifyApiList = responseDTO
						  .stream()
						  .filter(obj-> isValidApi(obj))
						  .collect(Collectors.toList());
				
				logger.info("notifyApiList.size()===> "+notifyApiList.size());
								
				notifyApiList.stream().forEach(apiInfo -> {
					EmailRequest req = new EmailRequest();
					req.setApiName(apiInfo.getApiName());
					req.setEmailAddr(apiInfo.getEmailAddress());
					req.setSwagger(isBlank(apiInfo.getApiSwagUrl()) ? "false" : "true");
					req.setModel(apiInfo.getApiModels().isEmpty() ? "false" : "true");
					notifyReqList.add(req);
				});
				
				notifyReqList.forEach(System.out::println);

				/*notifyReqList.stream().forEach(req -> {
					if (req.getEmailAddr()!=null)
						jmsTemplate.convertAndSend(Constants.MESSAGE_DESTINATION_NAME, req);
				});*/
			}
		}

		private boolean isValidApi(ResponseDTO responseDTO) {
			if (responseDTO.getApiSwagUrl() == null || responseDTO.getApiSwagUrl().isEmpty()) {
				logger.info("responseDTO.getApiSwagUrl() is empty");
				return false;
			}
			if (responseDTO.getApiModels() == null || responseDTO.getApiModels().size() == 0) {
				logger.info("responseDTO.getApiModels() is empty");
				return false;
			}

			return true;
		}

		private boolean isBlank(String apiSwagUrl) {
			// TODO Auto-generated method stub
			if (apiSwagUrl == null)
				return true;
			if (apiSwagUrl.isEmpty())
				return true;

			return false;
		}
	}
}
