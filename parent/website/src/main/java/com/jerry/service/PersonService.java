package com.jerry.service;

import java.util.List;

import com.jerry.bean.model.Person;

public interface PersonService {

	public List<Person> findByType(String type);
	
}
