package com.jerry.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jerry.bean.model.Person;
import com.jerry.service.PersonService;

@Controller
@RequestMapping(value="/person")
public class PersonAction extends AbstractAction {

	@Resource
	private PersonService personService;
	
	@RequestMapping(value="/list/{type}")
	@ResponseBody
	public List<Person> list(@PathVariable String type){
		return personService.findByType(type);
	}
	
}
