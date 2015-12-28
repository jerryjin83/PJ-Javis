package com.jerry.common.exceptions;

import com.jerry.common.BusinessException;

public class PersonNotFoundException extends BusinessException{

	public PersonNotFoundException(String string) {
		super(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3266114273193993299L;

}
