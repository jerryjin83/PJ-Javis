package com.jerry.dao;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.jerry.bean.model.IModel;
import com.jerry.common.BeanUtil;
import com.jerry.common.Page;
import com.jerry.exception.BeanConvertException;
import com.jerry.exception.PersonNotFoundException;
import com.jerry.exception.UpdateException;

public abstract class AbstractMongoDao<T extends IModel> implements IDao<T>{

	@Resource
	private MongoTemplate mongoTemplate;

	protected abstract Class<T> getModalClass();
	
	@Override
	public long count(Map<String, Object> queryParam){
		Query query = new Query();
		if(queryParam!=null){
			for(String field:queryParam.keySet()){
				query.addCriteria(Criteria.where(field).is(queryParam.get(field)));
			}
		}
		return getMongoTemplate().count(query, getModalClass());
	}
	
	@Override
	public void insert(T t) {
		getMongoTemplate().insert(t);
	}

	@Override
	public T findOne(String id) {
		return getMongoTemplate().findOne(
				new Query(Criteria.where("id").is(id)), getModalClass());
	}

	@Override
	public List<T> findAll() {
		return getMongoTemplate().find(new Query(), getModalClass());
	}

	@Override
	public List<T> findByRegex(String field,String regex) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Criteria criteria = new Criteria(field).regex(pattern.toString());
		return getMongoTemplate().find(new Query(criteria), getModalClass());
	}
	
	@Override
	public T findOneByKeyValue(String field,String value) {
		Criteria criteria = new Criteria(field).is(value);
		return getMongoTemplate().findOne(new Query(criteria), getModalClass());
	}
	
	@Override
	public List<T> findAllByKeyValue(String field,String value) {
		Criteria criteria = new Criteria(field).is(value);
		return getMongoTemplate().find(new Query(criteria), getModalClass());
	}

	@Override
	public void removeOne(String id){
		T t = findOne(id);
		if(t==null){
			throw new PersonNotFoundException(getModalClass() + ":id="+id+",not found!");
		}
		getMongoTemplate().remove(t);

	}

	@Override
	public void removeAll() {
		List<T> list = this.findAll();
		if (list != null) {
			for (T person : list) {
				getMongoTemplate().remove(person);
			}
		}
	}

	@Override
	public void update(T obj) throws UpdateException {
		Query query = new Query(Criteria.where("id").is(obj.getId()));
		Update update;
		try {
			update = BeanUtil.convertToUpdate(obj);
		} catch (BeanConvertException e) {
			throw new UpdateException("Update bean failed.",e);
		}
		getMongoTemplate().updateFirst(query, update,getModalClass());  
	}

	@Override
	public Page<T> findByPage(Map<String, Object> queryParam, int pageNumber,
			int pageSize) {
		Page<T> page = new Page<T>();
		
		Query query = new Query();
		query.skip((pageNumber-1)*pageSize);
		query.limit(pageSize);
		if(queryParam!=null){
			for(String field:queryParam.keySet()){
				query.addCriteria(Criteria.where(field).is(queryParam.get(field)));
			}
		}
		List<T> result = getMongoTemplate().find(query, getModalClass());
		page.setResult(result);
		page.setTotal(count(queryParam));
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		return page;
	}

	@Override
	public List<T> findAllBy(Map<String, Object> queryParam) {
		Query query = BeanUtil.convertToQuery(queryParam);
		return getMongoTemplate().find(query, getModalClass());
	}

	@Override
	public T findOneBy(Map<String, Object> queryParam) {
		Query query = BeanUtil.convertToQuery(queryParam);
		return getMongoTemplate().findOne(query, getModalClass());
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
}
