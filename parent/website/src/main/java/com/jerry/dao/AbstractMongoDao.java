package com.jerry.dao;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public abstract class AbstractMongoDao<T> implements IDao<T>{

	@Resource
	private MongoTemplate mongoTemplate;

	protected abstract Class<T> getModalClass();
	
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
	public void removeOne(String id) {
		Criteria criteria = Criteria.where("id").in(id);
		if (criteria == null) {
			Query query = new Query(criteria);
			if (query != null
					&& getMongoTemplate().findOne(query, getModalClass()) != null)
				getMongoTemplate().remove(
						getMongoTemplate().findOne(query, getModalClass()));
		}

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
	public void findAndModify(String id,Map<String,Object> values) {
		for(String key:values.keySet()){
			getMongoTemplate().updateFirst(new Query(Criteria.where("id").is(id)), new Update().set(key, values.get(key)),getModalClass());  
		}
	}

	@Override
	public List<T> findByPage(Map<String, String> queryParam, int start,
			int pageSize) {
		Query query = new Query();
		query.skip(start-1);
		query.limit(pageSize);
		for(String field:queryParam.keySet()){
			query.addCriteria(Criteria.where(field).is(queryParam.get(field)));
		}
		return getMongoTemplate().find(query, getModalClass());
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
}
