package com.jerry.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.jerry.common.BaseSpringTestCase;

public class PersonDaoImplTest extends BaseSpringTestCase{
	@Resource
	PersonDaoImpl personDaoImpl;
	
	@Test
	public void testFindByPage() {
		Map<String ,String> params = new HashMap<String,String>();
		params.put("name","jerry");
		personDaoImpl.findByPage(params, 0, 10);
	}
	
	@Test
	public void testFind(){
		personDaoImpl.findOneByKeyValue("name", "jerry");
	}

}
