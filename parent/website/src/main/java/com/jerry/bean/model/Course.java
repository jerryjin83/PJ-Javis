package com.jerry.bean.model;

import java.util.Date;
import java.util.List;

public class Course extends AbstractModel{
	private String number;
	private String name;
	private Date startDate;
	private Date endDate;
	private String startTime;
	private String endTime;
	private String classroom;
	private Person teacher;
	private List<Person> student;
	private int total;
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Person getTeacher() {
		return teacher;
	}
	public void setTeacher(Person teacher) {
		this.teacher = teacher;
	}
	public List<Person> getStudent() {
		return student;
	}
	public void setStudent(List<Person> student) {
		this.student = student;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getClassroom() {
		return classroom;
	}
	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
	
}
