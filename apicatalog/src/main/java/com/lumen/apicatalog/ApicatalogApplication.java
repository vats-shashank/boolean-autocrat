package com.lumen.apicatalog;

import javax.jms.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.util.ErrorHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableJms
@SpringBootApplication
public class ApicatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApicatalogApplication.class, args);
	}
	
	@Bean
	public JmsListenerContainerFactory<?> apiCatalogFactory(
	    ConnectionFactory connectionFactory,
	    DefaultJmsListenerContainerFactoryConfigurer configurer) {
	  DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

	  // anonymous class
	  factory.setErrorHandler(
	      new ErrorHandler() {
	        @Override
	        public void handleError(Throwable t) {
	          System.err.println("An error has occurred in the transaction");
	        }
	      });

	  // lambda function
	  factory.setErrorHandler(t -> System.err.println("An error has occurred in the transaction"));

	  configurer.configure(factory, connectionFactory);
	  return factory;
	}

	
	// Serialize message content to json using TextMessage
	  @Bean
	  public MessageConverter jacksonJmsMessageConverter() {
	    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	    converter.setTargetType(MessageType.TEXT);
	    converter.setTypeIdPropertyName("_type");
	    return converter;
	  }
	  
}
