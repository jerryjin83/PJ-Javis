package com.jerry.common;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Update;

import com.jerry.bean.model.Person;
import com.jerry.exception.BeanConvertException;

public class BeanUtilTest {
	@Test
	public void test(){
		try {
			Update update = BeanUtil.convertToUpdate(new Person());
			Assert.assertNotNull("Bean convert to update success.",update);
		} catch (BeanConvertException e) {
			e.printStackTrace();
			Assert.assertFalse("Bean convert to update failed",true);
		}
		
	}
}
