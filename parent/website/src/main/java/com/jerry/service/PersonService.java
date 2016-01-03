package com.jerry.service;

import java.util.List;
import java.util.Map;

import com.jerry.bean.model.Person;
import com.jerry.bean.view.PersonView;
import com.jerry.common.Page;

public interface PersonService {
	
	public void insert(Person person);

	public List<Person> findByType(String type);
	
	public List<Person> findAllby(Map<String,Object> queryParam);
	
	public Page<PersonView> queryByPage(Map<String,Object> queryParam,int pageNumber,int pageSize);
	
	public void delete(String id);
	
	public void update(Person person);
	
}
