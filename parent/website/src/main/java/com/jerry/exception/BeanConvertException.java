package com.jerry.exception;

public class BeanConvertException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3254964874874214310L;

	public BeanConvertException(){}
	
	public BeanConvertException(String message,Throwable e){
		super(message,e);
	}
	
	public BeanConvertException(Throwable e){
		super(e);
	}
}
