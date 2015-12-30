package com.jerry.common;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Update;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerry.exception.BeanConvertException;

public class BeanUtil {	
	
	/**
	 * Convert bean to mongodb Update
	 * @param obj
	 * @return
	 * @throws BeanConvertException
	 */
	public static Update convertToUpdate(Object obj) throws BeanConvertException{
		Update update = new Update();
		Class<?> clazz = obj.getClass();
		for(Method method:clazz.getMethods()){
			String methodName = method.getName();
			if(methodName.startsWith("is")||methodName.startsWith("get")){
				PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method, clazz);
				try {
					// if the current field doesn't have a write method, then ignore it.
					if(pd.getWriteMethod()==null || pd.getName().equals("id") || pd.getName().equals("_class")){
						continue;
					}
					update.set(pd.getName(),method.invoke(obj));
				} catch (Exception e) {
					throw new BeanConvertException("Bean convert failed!",e);
				}
			}
		}
		
		return update;
	}
	
	public static String convertToJsonString(Object obj) throws BeanConvertException{
		ObjectMapper mapper = new ObjectMapper();  
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new BeanConvertException("Object convert to json failed",e);
		}
	}

}
