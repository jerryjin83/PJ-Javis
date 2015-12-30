package com.jerry.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jerry.bean.form.CourseForm;
import com.jerry.bean.form.PageQueryForm;
import com.jerry.bean.model.Course;
import com.jerry.bean.model.Person;
import com.jerry.bean.view.CourseView;
import com.jerry.common.Page;
import com.jerry.exception.PersonNotFoundException;
import com.jerry.service.CourseService;
import com.jerry.service.PersonService;
import com.jerry.web.bean.ResultBean;

@Controller
@RequestMapping("/course")
public class CourseAction extends AbstractAction{
	
	private Logger logger = Logger.getLogger(getClass());
	
	private static final String LIST = "course/list";
	
	@Resource(name="courseService")
	private CourseService courseService;
	
	@Resource
	private PersonService personService;

	@RequestMapping(value="/list")
	public String list(PageQueryForm form,Model model){
		Page<CourseView> page = courseService.queryByPage(form.getQueryParam(), form.getPageNumber(), form.getPageSize()==0?Page.DEFAULT_PAGE_SIZE:form.getPageSize());
		List<Person> teacher = personService.findByType("2");
		model.addAttribute("teachers", teacher);
		model.addAttribute("page", page);
		return LIST;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public ResultBean delete(String id){
		ResultBean rb = new ResultBean();
		try{
			courseService.delete(id);
			rb.setSuccess(true);
		}catch(PersonNotFoundException e){
			logger.error("更新用户失败，没有找到该用户!",e);
			rb.setMessage("对不起，没有找到该用户!");
			rb.setSuccess(false);
		}
		return rb;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	public ResultBean update(CourseForm form){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ResultBean rb = new ResultBean();
		try{
			Course course = new Course();
			course.setId(form.getId());
			course.setStartDate(sdf.parse(form.getStartDate()));
			course.setName(form.getName());
			course.setNumber(form.getNumber());
			course.setEndDate(sdf.parse(form.getEndDate()));
			course.setTotal(Integer.parseInt(form.getTotal()));
			courseService.update(course,form.getTeacher());
			rb.setSuccess(true);
		}catch(PersonNotFoundException e){
			logger.error("更新用户失败，没有找到该用户!",e);
			rb.setMessage("对不起，没有找到该用户!");
			rb.setSuccess(false);
		} catch (ParseException e) {
			logger.error("更新用户失败，日期解析出错",e);
			rb.setMessage("更新用户失败，请联系管理员!");
			rb.setSuccess(false);
		}
		return rb;
	}
	
	@RequestMapping(value="/insert")
	@ResponseBody
	public ResultBean insert(CourseForm form){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ResultBean rb = new ResultBean();
		try{
			Course course = new Course();
			course.setStartDate(sdf.parse(form.getStartDate()));
			course.setName(form.getName());
			course.setNumber(form.getNumber());
			course.setEndDate(sdf.parse(form.getEndDate()));
			course.setTotal(Integer.parseInt(form.getTotal()));
			courseService.insert(course,form.getTeacher());
			rb.setSuccess(true);
		}catch(Exception e){
			logger.error("更新用户失败，未知错误!",e);
			rb.setMessage("新增用户失败，请联系管理员！");
			rb.setSuccess(false);
		}
		return rb;
	}
}
