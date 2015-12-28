package com.jerry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courseManagement")
public class CourseManagementAction {
	
	private static final String LIST = "courseManagement/list";

	@RequestMapping(value="/list")
	public String list(){
		return LIST;
	}
	
}
