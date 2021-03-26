package com.zipe.common.payload;

import lombok.Data;

@Data
public class ValidationErrorResponse {

	public ValidationErrorResponse(String fieldName, String errorMessage) {
		this.fieldName = fieldName;
		this.errorMessage = errorMessage;
	}

	String fieldName;
	String errorMessage;

}
