package com.jerry.service;

import java.util.Map;

import com.jerry.bean.model.Course;
import com.jerry.bean.view.CourseView;
import com.jerry.common.Page;

public interface CourseService {

	public void insert(Course course,String teacher);
	
	public Page<CourseView> queryByPage(Map<String,String> queryParam,int pageNumber,int pageSize);
	
	public void delete(String id);
	
	public void update(Course course,String teracher);
	
}
