package com.jerry.bean.view;

import java.text.SimpleDateFormat;

import com.jerry.bean.model.Person;

public class PersonView {
	private String id;
	private String username;
	private String fullName;
	private String gander;
	private String major;
	private String department;
	private String birthdate;
	
	public PersonView(Person person){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.id = person.getId();
		this.username = person.getUsername();
		this.fullName = person.getFullName();
		this.gander = person.getGander();
		this.major = person.getMajor();
		this.department = person.getDepartment();
		this.birthdate = sdf.format(person.getBirthdate());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGander() {
		return gander;
	}

	public void setGander(String gander) {
		this.gander = gander;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
}
