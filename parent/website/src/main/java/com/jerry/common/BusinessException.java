package com.jerry.common;

public class BusinessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6556208203216953324L;

	private String message;
	
	private String errorCode;
	
	public BusinessException(){
		
	}
	
	public BusinessException(String message){
		this.message = message;
	}
	
	public BusinessException(String errorCode,String message){
		this.message = message;
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
