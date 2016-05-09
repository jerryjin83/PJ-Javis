package com.jerry.jandj.controller.bean;

import com.jerry.bean.model.Person;


public class WebsiteSession {

	private boolean isSignin = false;

	private Person person;
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public boolean getIsSignin() {
		return isSignin;
	}

	public void setSignin(boolean isSignin) {
		this.isSignin = isSignin;
	}
	
}
