package com.jerry.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jerry.bean.form.CourseForm;
import com.jerry.bean.form.PageQueryForm;
import com.jerry.bean.form.PersonForm;
import com.jerry.bean.model.Course;
import com.jerry.bean.model.Person;
import com.jerry.bean.view.CourseView;
import com.jerry.bean.view.PersonView;
import com.jerry.common.MD5Util;
import com.jerry.common.Page;
import com.jerry.exception.PersonNotFoundException;
import com.jerry.service.CourseService;
import com.jerry.service.PersonService;
import com.jerry.web.bean.ResultBean;

@Controller
@RequestMapping("/management")
public class ManagementAction extends AbstractAction{
	
	private Logger logger = Logger.getLogger(getClass());
	
	private static final String STUDENT = "management/student";
	private static final String TEACHER = "management/teacher";
	
	@Resource(name="courseService")
	private CourseService courseService;
	
	@Resource
	private PersonService personService;

	@RequestMapping(value="/student")
	public String student(PageQueryForm form,Model model,String name,String teacher){
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(name))
			param.put("fullName", name);
		param.put("type", "3");
		Page<PersonView> page = personService.queryByPage(param, form.getPageNumber(), form.getPageSize()==0?Page.DEFAULT_PAGE_SIZE:form.getPageSize());
		model.addAttribute("name",name);
		model.addAttribute("page", page);
		return STUDENT;
	}
	
	@RequestMapping(value="/teacher")
	public String teacher(PageQueryForm form,Model model,String name,String teacher){
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtils.isNotEmpty(name))
			param.put("fullName", name);
		param.put("type", "2");
		Page<PersonView> page = personService.queryByPage(param, form.getPageNumber(), form.getPageSize()==0?Page.DEFAULT_PAGE_SIZE:form.getPageSize());
		model.addAttribute("name",name);
		model.addAttribute("page", page);
		return TEACHER;
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	public ResultBean delete(String id){
		ResultBean rb = new ResultBean();
		try{
			personService.delete(id);
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
	public ResultBean update(PersonForm form){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ResultBean rb = new ResultBean();
		try{
			Person person = new Person();
			person.setBirthdate(sdf.parse(form.getBirthdate()));
			person.setDepartment(form.getDepartment());
			person.setGander(form.getGander());
			person.setMajor(form.getMajor());
			person.setId(form.getId());
			personService.update(person);
			rb.setSuccess(true);
		} catch (ParseException e) {
			logger.error("更新用户失败，日期解析出错",e);
			rb.setMessage("更新用户失败，生日格式错误!");
			rb.setSuccess(false);
		} catch(Exception e){
			logger.error("更新用户失败，未知错误",e);
			rb.setMessage("更新用户失败，请联系管理员！");
			rb.setSuccess(false);
		}
		return rb;
	}
	
	@RequestMapping(value="/insert")
	@ResponseBody
	public ResultBean insert(PersonForm form){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ResultBean rb = new ResultBean();
		try{
			Map<String,Object> param = new HashMap<String,Object>();
			if(StringUtils.isNotEmpty(form.getUsername()))
				param.put("username", form.getUsername());
			List<Person> persons = personService.findAllby(param);
			if(persons.size() == 0){
				Person person = new Person();
				person.setUsername(form.getUsername());
				person.setDepartment(form.getDepartment());
				person.setFullName(form.getFullName());
				person.setGander(form.getGander());
				person.setMajor(form.getMajor());
				person.setBirthdate(sdf.parse(form.getBirthdate()));
				person.setType(form.getType());
				person.setPassword(MD5Util.MD5("123456"));
				personService.insert(person);
				rb.setSuccess(true);
			}else{
				logger.debug("用户已存在，无法新增!");
				rb.setMessage("用户已存在，无法新增，请更换用户名再试！");
				rb.setSuccess(false);
			}
		}catch(Exception e){
			logger.error("新增用户失败，未知错误!",e);
			rb.setMessage("新增用户失败，请联系管理员！");
			rb.setSuccess(false);
		}
		return rb;
	}
}
