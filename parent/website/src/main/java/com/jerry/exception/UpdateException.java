package com.jerry.exception;

public class UpdateException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8090634846267412371L;

	public UpdateException() {
	}

	public UpdateException(String message,Throwable e){
		super(message,e);
	}

	public UpdateException(Throwable e) {
		super(e);
	}
}
