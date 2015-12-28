package com.jerry.common.exceptions;

import com.jerry.common.BusinessException;

public class PasswordNotCorrectException extends BusinessException{

	public PasswordNotCorrectException(String string) {
		super(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4840878817389337044L;

}
