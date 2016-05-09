package com.jerry.service;

import com.jerry.bean.model.Person;


public interface LoginService {
	/**
	 * check the username and password is correct or not
	 * @param username
	 * @param password
	 */
	public Person doLogin(String username,String password);
}
