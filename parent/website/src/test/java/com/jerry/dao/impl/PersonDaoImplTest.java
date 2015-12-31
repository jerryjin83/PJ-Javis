package com.jerry.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.jerry.bean.model.Person;
import com.jerry.common.BaseSpringTestCase;
import com.jerry.common.MD5Util;
import com.jerry.common.Page;
import com.jerry.exception.UpdateException;

public class PersonDaoImplTest extends BaseSpringTestCase{
	@Resource
	PersonDaoImpl personDaoImpl;
	
	@Test
	public void testFindByPage() {
		Map<String ,Object> params = new HashMap<String,Object>();
		params.put("username","jerry");
		Page<Person> persons = personDaoImpl.findByPage(params, 0, 10);
		System.out.println(persons);
	}
	
	@Test
	public void testFindOne(){
		personDaoImpl.findOneByKeyValue("username", "admin");
	}
	
	@Test
	public void testFindAll(){
		List<Person> person = personDaoImpl.findAll();
		Assert.assertNotNull("Person.testFindAll passed.",person);
	}
	
	@Test
	public void testUpdate(){
		Person admin = personDaoImpl.findOneByKeyValue("username", "admin");
		admin.setPassword(MD5Util.MD5(admin.getPassword()));
		try {
			personDaoImpl.update(admin);
		} catch (UpdateException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateAllPasswordToMd5(){
		List<Person> persons = personDaoImpl.findAll();
		try {
			for(Person person:persons){
				if(person.getPassword().length()<32){
					person.setPassword(MD5Util.MD5(person.getPassword()));
					personDaoImpl.update(person);
				}
			}
		} catch (UpdateException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testInsert(){
		Person person = new Person();
		person.setUsername("zhangsan");
		person.setFullName("张三");
		person.setGander("男");
		person.setBirthdate(new Date());
		person.setPassword(MD5Util.MD5("123456"));
		person.setType("2");
		personDaoImpl.insert(person);
	}
	
	@Test
	public void testInsertAdmin(){
		Person person = new Person();
		person.setUsername("admin");
		person.setFullName("管理员");
		person.setGander("男");
		person.setBirthdate(new Date());
		person.setPassword(MD5Util.MD5("123456"));
		person.setType("1");
		personDaoImpl.insert(person);
	}
	
	@Test
	public void testDelete(){
		String username = System.currentTimeMillis()+"user";
		Person person = new Person();
		person.setUsername(username);
		person.setFullName("管理员");
		person.setGander("男");
		person.setBirthdate(new Date());
		person.setPassword(MD5Util.MD5("123456"));
		person.setType("1");
		personDaoImpl.insert(person);
		
		Person p = personDaoImpl.findOneByKeyValue("username",username);
		personDaoImpl.removeOne(p.getId());
	}

}
