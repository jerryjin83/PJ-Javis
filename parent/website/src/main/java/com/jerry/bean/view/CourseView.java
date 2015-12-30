package com.jerry.bean.view;

import java.text.SimpleDateFormat;
import java.util.List;

import com.jerry.bean.model.Course;
import com.jerry.bean.model.Person;
import com.jerry.common.BeanUtil;


public class CourseView {
	private String id;
	private String number;
	private String name;
	private String startDate;
	private String endDate;
	private String teacher;
	private String teacherId;
	private int count;
	private int total;
	public CourseView(Course course) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.number = course.getNumber();
		this.id = course.getId();
		this.name = course.getName();
		this.startDate = sdf.format(course.getStartDate());
		this.endDate =sdf.format(course.getEndDate());
		this.teacher = course.getTeacher().getFullName();
		this.teacherId = course.getTeacher().getId();
		List<Person> students = course.getStudent();
		this.count = students==null?0:students.size();
		this.total = course.getTotal();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String toString(){
		return BeanUtil.convertToJsonString(this);
	}
	
}
