package com.jerry.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jerry.bean.modal.Person;
import com.jerry.common.WebsiteSession;

public abstract class AbstractController {
	private static final String SESSION = "website";
	public WebsiteSession getSession(HttpServletRequest request){
		Object session = request.getSession().getAttribute(SESSION);
		return session==null?null:(WebsiteSession)session;
	}
	
	protected void initSession(HttpServletRequest request,Person person){
		HttpSession s = request.getSession();
		s.setMaxInactiveInterval(0);
		WebsiteSession session = new WebsiteSession();
		session.setPerson(person);
		session.setSignin(true);
		// init login 
		s.setAttribute(SESSION, session);
		
	}
	
	protected void clear(HttpServletRequest request){
		HttpSession s = request.getSession();
		s.removeAttribute(SESSION);
		s.setMaxInactiveInterval(1);
	}
}
