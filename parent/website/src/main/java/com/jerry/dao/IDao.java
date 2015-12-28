package com.jerry.dao;

import java.util.List;
import java.util.Map;

public interface IDao<T> {  
    public void insert(T t);  
    public T findOne(String id); 
    public T findOneByKeyValue(String field,String value);
    public List<T> findAll();  
    public List<T> findByPage(Map<String,String> queryParam,int start,int pageSize);
    public List<T> findByRegex(String field,String regex);  
    public void removeOne(String id);  
    public void removeAll();  
    public void findAndModify(String id,Map<String,Object> values);  
}  
