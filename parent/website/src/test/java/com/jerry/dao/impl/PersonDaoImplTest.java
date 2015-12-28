package com.jerry.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.jerry.bean.modal.Person;
import com.jerry.common.BaseSpringTestCase;

public class PersonDaoImplTest extends BaseSpringTestCase{
	@Resource
	PersonDaoImpl personDaoImpl;
	
	@Test
	public void testFindByPage() {
		Map<String ,String> params = new HashMap<String,String>();
		List<Person> list = personDaoImpl.findByPage(params, 2, 10);
		Assert.assertTrue(list.size()>0);
	}
	
	@Test
	public void testFind(){
		personDaoImpl.findOneByKeyValue("name", "jerry");
	}

}
