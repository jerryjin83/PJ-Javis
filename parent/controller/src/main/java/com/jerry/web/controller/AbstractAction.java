package com.jerry.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jerry.bean.model.Person;
import com.jerry.web.controller.bean.WebsiteSession;

public abstract class AbstractAction {
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
		s.setAttribute("context",request.getContextPath());
	}
	
	protected Person getCurrentLoginUser(HttpServletRequest request){
		return getSession(request).getPerson();
	}
	
	protected void clear(HttpServletRequest request){
		HttpSession s = request.getSession();
		s.removeAttribute(SESSION);
		s.setMaxInactiveInterval(1);
	}
}
