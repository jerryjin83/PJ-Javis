package com.jerry.dao.impl;

import org.springframework.stereotype.Repository;

import com.jerry.bean.model.Course;
import com.jerry.dao.AbstractMongoDao;
import com.jerry.dao.CourseDao;

@Repository(value="courseDao")
public class CourseDaoImpl extends AbstractMongoDao<Course> implements CourseDao {

	@Override
	protected Class<Course> getModalClass() {
		return Course.class;
	}
}
