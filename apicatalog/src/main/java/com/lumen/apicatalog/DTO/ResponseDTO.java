package com.lumen.apicatalog.DTO;

import java.io.Serializable;
import java.util.List;

import com.lumen.apicatalog.model.ApiModel;

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

	String API_NAME;
	String API_DESC;
	String SWAGGER_URL;
	String API_STATUS;
	String API_ID;
	
	String APP_ID;	
	String APP_NAME;
	String USER_ID;
	String CUID;
	String EMAIL_ADDRESS;
	String CATEGORY_ID;
	String CATEGORY_NAME;
	
	List<ApiModelDTO> API_MODEL;
	
}
