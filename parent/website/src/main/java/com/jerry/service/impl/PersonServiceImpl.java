package com.jerry.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jerry.bean.model.Person;
import com.jerry.dao.PersonDao;
import com.jerry.service.PersonService;

@Service(value="personService")
public class PersonServiceImpl implements PersonService{
	
	@Resource
	private PersonDao personDao;

	@Override
	public List<Person> findByType(String type) {
		return personDao.findAllByKeyValue("type", type);
	}

}
