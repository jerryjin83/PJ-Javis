package com.jerry.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jerry.bean.model.Person;
import com.jerry.bean.view.PersonView;
import com.jerry.common.Page;
import com.jerry.dao.PersonDao;
import com.jerry.exception.BusinessException;
import com.jerry.exception.UpdateException;
import com.jerry.service.PersonService;

@Service(value="personService")
public class PersonServiceImpl implements PersonService{
	
	@Resource
	private PersonDao personDao;
	
	@Override
	public void insert(Person person) {
		personDao.insert(person);
	}
	
	@Override
	public void delete(String id) {
		personDao.removeOne(id);
	}

	@Override
	public void update(Person person) {
		Person oldPerson = personDao.findOne(person.getId());
		try {
			oldPerson.setBirthdate(person.getBirthdate());
			oldPerson.setDepartment(person.getDepartment());
			oldPerson.setGander(person.getGander());
			oldPerson.setMajor(person.getMajor());
			personDao.update(oldPerson);
		} catch (UpdateException e) {
			throw new BusinessException("","更新课程信息失败",e);
		}
	}

	@Override
	public List<Person> findByType(String type) {
		return personDao.findAllByKeyValue("type", type);
	}
	
	@Override
	public List<Person> findAllby(Map<String,Object> queryParam){
		return personDao.findAllBy(queryParam);
	}

	@Override
	public Page<PersonView> queryByPage(Map<String,Object> queryParam, int pageNumber, int pageSize) {
		Page<PersonView> page = new Page<PersonView>();
		Page<Person> cp =  personDao.findByPage(queryParam, pageNumber, pageSize);
		List<PersonView> viewl = new ArrayList<PersonView>();
		for(Person person:cp.getResult()){
			viewl.add(new PersonView(person));
		}
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setResult(viewl);
		page.setTotal(cp.getTotal());
		return page;
	}
}
