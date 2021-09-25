package com.lumen.apicatalog.DTO;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author Pooja
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String TJAM_APP_NAME;
	String CATEGORY_NAME;
	String MODEL_NAME;
	String MODEL_STATUS;
	
	String API_NAME;
	String API_ID;
	String API_STATUS;
	String API_DESC;
	String SWAGGER_URL;
	
	String USER_ID;
	String CATEGORY_ID;
	String APP_ID;
	String MODEL_ID;
	
	String EMAIL_ADDRESS;
}
