package com.jerry.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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
	public Page<CourseView> queryByPage(Map<String,Object> queryParam, int pageNumber, int pageSize) {
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
			throw new BusinessException("","更新课程信息失败",e);
		}
	}

	@Override
	public Page<CourseView> queryMyCourse(Map<String, Object> queryParam,
			int pageNumber, int pageSize, Person person) {
		if(person.getType().equals("2")){
			queryParam.put("teachre.username", person.getUsername());
		}
		if(person.getType().equals("3")){
			queryParam.put("student.username", person.getUsername());
		}
		Page<CourseView> page = new Page<CourseView>();
		Page<Course> cp =  courseDao.findByPage(queryParam, pageNumber, pageSize);
		List<CourseView> viewl = new ArrayList<CourseView>();
		for(Course course:cp.getResult()){
			CourseView cv = new CourseView(course);
			if(person.getType().equals("3")){
				List<Person> students = course.getStudent();
				for(Person student:students){
					if(student.getUsername().equals(person.getUsername())){
						cv.setScore(student.getScore());
						break;
					}
				}
			}
			viewl.add(cv);
		}
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setResult(viewl);
		page.setTotal(cp.getTotal());
		return page;
	}

	@Override
	public void pickup(String id, Person student) {
		Course course = courseDao.findOne(id);
		if(course!=null){
			Course c = queryMyCourse(id,student);
			if(c!=null){
				throw new BusinessException("","您已经选择了该课程");
			}
			List<Person> students = course.getStudent();
			if(students==null)
				students = new ArrayList<Person>();
			if(students.size()==course.getTotal()){
				throw new BusinessException("","该课程已经选满");
			}
			students.add(student);
			course.setStudent(students);
			try {
				courseDao.update(course);
			} catch (UpdateException e) {
				throw new BusinessException("","选课失败",e);
			}
		}else{
			throw new BusinessException("","没有找到该课程");
		}
	}
	
	private Course queryMyCourse(String id,Person student){
		Map<String,Object> queryParam = new HashMap<String,Object>();
		queryParam.put("id", id);
		queryParam.put("student.username", student.getUsername());
		Course c = courseDao.findOneBy(queryParam);
		return c;
	}

	@Override
	public Page<CourseView> queryCourse(Map<String, Object> param,
			int pageNumber, int pageSize, Person user) {
		Page<CourseView> page = new Page<CourseView>();
		Page<Course> cp =  courseDao.findByPage(param, pageNumber, pageSize);
		List<CourseView> viewl = new ArrayList<CourseView>();
		for(Course course:cp.getResult()){
			CourseView cv = new CourseView(course);
			if(queryMyCourse(course.getId(),user)!=null){
				cv.setPickedup(true);
			}
			viewl.add(cv);
		}
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setResult(viewl);
		page.setTotal(cp.getTotal());
		return page;
	}

}
