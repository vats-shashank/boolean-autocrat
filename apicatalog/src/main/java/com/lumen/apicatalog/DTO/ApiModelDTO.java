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
public class ApiModelDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	String modelId;
	String status;
	String modelName;
	String modelType;
	String apiId;
	
	/*String MODEL_ID;
	String MODEL_STATUS;
	String MODEL_NAME;
	String MODEL_TYPE;
	String API_Id;*/

}
