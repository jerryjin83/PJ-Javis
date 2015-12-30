package com.jerry.dao;

import java.util.List;
import java.util.Map;

import com.jerry.common.Page;
import com.jerry.exception.UpdateException;

public interface IDao<T> {  
    public void insert(T t);
    public long count(Map<String, String> queryParam);
    public T findOne(String id); 
    public T findOneByKeyValue(String field,String value);
    public List<T> findAll();  
    public Page<T> findByPage(Map<String,String> queryParam,int pageNumber,int pageSize);
    public List<T> findByRegex(String field,String regex);  
    public void removeOne(String id);  
    public void removeAll();  
    public void update(T obj) throws UpdateException;
	List<T> findAllByKeyValue(String field, String value);  
}  
