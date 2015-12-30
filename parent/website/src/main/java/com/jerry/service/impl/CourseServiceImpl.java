package com.jerry.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jerry.bean.model.Course;
import com.jerry.bean.model.Person;
import com.jerry.bean.view.CourseView;
import com.jerry.common.Page;
import com.jerry.dao.CourseDao;
import com.jerry.dao.PersonDao;
import com.jerry.exception.BusinessException;
import com.jerry.exception.UpdateException;
import com.jerry.service.CourseService;

@Service("courseService")
public class CourseServiceImpl implements CourseService {
	
	@Resource
	private CourseDao courseDao;
	
	@Resource
	private PersonDao personDao;
	
	@Override
	public void insert(Course course,String teacher) {
		Person person = personDao.findOne(teacher);
		course.setTeacher(person);
		courseDao.insert(course);
	}

	@Override
	public Page<CourseView> queryByPage(Map<String,String> queryParam, int pageNumber, int pageSize) {
		Page<CourseView> page = new Page<CourseView>();
		Page<Course> cp =  courseDao.findByPage(queryParam, pageNumber, pageSize);
		List<CourseView> viewl = new ArrayList<CourseView>();
		for(Course course:cp.getResult()){
			viewl.add(new CourseView(course));
		}
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setResult(viewl);
		page.setTotal(cp.getTotal());
		return page;
	}

	@Override
	public void delete(String id) {
		courseDao.removeOne(id);
	}

	@Override
	public void update(Course course,String teacher) {
		Course oldCourse = courseDao.findOne(course.getId());
		try {
			oldCourse.setEndDate(course.getEndDate());
			oldCourse.setStartDate(course.getStartDate());
			Person person = personDao.findOne(teacher);
			oldCourse.setTeacher(person);
			oldCourse.setTotal(course.getTotal());
			courseDao.update(oldCourse);
		} catch (UpdateException e) {
			throw new BusinessException("","",e);
		}
	}

}
