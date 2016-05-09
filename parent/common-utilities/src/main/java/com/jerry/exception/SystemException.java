package com.jerry.exception;

public class SystemException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2781120304939503438L;

	public SystemException(){}

	
	public SystemException(String message){
		super(message);
	}
	
	public SystemException(String message,Throwable e){
		super(message,e);
	}
	
	public SystemException(Throwable e){
		super(e);
	}
}
