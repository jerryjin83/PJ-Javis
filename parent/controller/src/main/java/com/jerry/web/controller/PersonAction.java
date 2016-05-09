package com.jerry.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jerry.bean.form.PageQueryForm;
import com.jerry.bean.model.Person;
import com.jerry.bean.view.CourseView;
import com.jerry.common.Page;
import com.jerry.exception.BusinessException;
import com.jerry.service.CourseService;
import com.jerry.service.PersonService;
import com.jerry.web.controller.bean.ResultBean;

@Controller
@RequestMapping(value="/person")
public class PersonAction extends AbstractAction {

	@Resource
	private PersonService personService;
	
	@Resource
	private CourseService courseService;
	
	@RequestMapping(value="/pickupCourse")
	public String pickupCourse(PageQueryForm form,String name,String teacher, Model model,HttpServletRequest request){
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(name))
			param.put("name", name);
		if(StringUtils.isNotEmpty(teacher))
			param.put("teacher.fullName", teacher);
		Person user = getCurrentLoginUser(request);
		Page<CourseView> page = courseService.queryCourse(param, form.getPageNumber(), form.getPageSize(),user);
		model.addAttribute("page", page);
		model.addAttribute("name", name);
		model.addAttribute("teacher", teacher);
		return "person/pickupCourse";
	}

	@RequestMapping(value="/pickup")
	@ResponseBody
	public ResultBean pickup(String id,HttpServletRequest request){
		ResultBean result = new ResultBean();
		try{
			courseService.pickup(id, getCurrentLoginUser(request));
			result.setSuccess(true);
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value="/myCourse")
	public String myCourse(PageQueryForm form, Model model,HttpServletRequest request){
		Person user = getCurrentLoginUser(request);
		return user.getType().equals("2")?"person/teacherCourse":"person/studentCourse";
	}
	
	@RequestMapping(value="/getCourse")
	@ResponseBody
	public Page<CourseView> getCourse(PageQueryForm form, Model model,HttpServletRequest request){
		Map<String,Object> map = new HashMap<String,Object>();
		Person user = getCurrentLoginUser(request);
		Page<CourseView> page = courseService.queryMyCourse(map, form.getPageNumber(), form.getPageSize(),user);
		return page;
	}
	
	@RequestMapping(value="/getMyStudents")
	@ResponseBody
	public Page<Person> getMyStudents(HttpServletRequest request,String id){
		Person user = getCurrentLoginUser(request);
		return courseService.getStudents(user.getUsername(),id);
	}
	
	@RequestMapping(value="/updateScore")
	@ResponseBody
	public ResultBean getMyStudents(HttpServletRequest request,String courseId,String score,String studentId){
		ResultBean result = new ResultBean();
		try{
			courseService.updateScore(courseId, score,studentId);
			result.setSuccess(true);
		}catch(BusinessException e){
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
}
