package com.jerry.dao.impl;

import org.springframework.stereotype.Repository;

import com.jerry.bean.model.Person;
import com.jerry.dao.AbstractMongoDao;
import com.jerry.dao.PersonDao;

@Repository(value="personDao")
public class PersonDaoImpl extends AbstractMongoDao<Person> implements PersonDao{

	@Override
	protected Class<Person> getModalClass() {
		return Person.class;
	}
}
