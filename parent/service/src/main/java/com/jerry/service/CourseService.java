package com.jerry.service;

import java.util.Map;

import com.jerry.bean.model.Course;
import com.jerry.bean.model.Person;
import com.jerry.bean.view.CourseView;
import com.jerry.common.Page;

public interface CourseService {

	public void insert(Course course,String teacher);
	
	public Page<CourseView> queryByPage(Map<String,Object> queryParam,int pageNumber,int pageSize);
	
	public void delete(String id);
	
	public void update(Course course,String teracher);
	
	public Page<CourseView> queryMyCourse(Map<String,Object> queryParam,int pageNumber,int pageSize,Person person);
	
	public void pickup(String id,Person student);

	/**
	 * 查询所有的课程
	 * @param param
	 * @param pageNumber
	 * @param pageSize
	 * @param user
	 * @return
	 */
	public Page<CourseView> queryCourse(Map<String, Object> param,
			int pageNumber, int pageSize, Person user);

	public void existed(String number);
	
	public Page<Person> getStudents(String username,String courseId);

	public void updateScore(String courseId, String score, String studentId);
	
}
