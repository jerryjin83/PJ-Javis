package com.jerry.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.jerry.bean.modal.Person;
import com.jerry.common.MD5Util;
import com.jerry.common.exceptions.PasswordNotCorrectException;
import com.jerry.common.exceptions.PersonNotFoundException;
import com.jerry.dao.PersonDao;
import com.jerry.service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService{
	private Logger logger = Logger.getLogger(getClass());
	@Resource(name="personDao")
	private PersonDao personDao;
	public Person doLogin(String username,String password){
		logger.debug("Start to do login. username="+username+", password="+password);
		Person person = personDao.findOneByKeyValue("username", username);
		if(person==null){
			throw new PersonNotFoundException("用户["+username+"]没有找到！");
		}
		if(!person.getPassword().equals(MD5Util.MD5(password))){
			throw new PasswordNotCorrectException("登录密码不正确!");
		}
		logger.debug("End of do login.");
		return person;
	}
	
}
