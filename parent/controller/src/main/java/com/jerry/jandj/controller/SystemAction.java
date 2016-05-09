package com.jerry.jandj.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jerry.bean.model.Person;
import com.jerry.exception.PasswordNotCorrectException;
import com.jerry.exception.PersonNotFoundException;
import com.jerry.jandj.controller.form.LoginForm;
import com.jerry.service.LoginService;

@Controller
@RequestMapping(value = "/system")
public class SystemAction extends AbstractAction {

	private static final String HOME = "home";

	@Resource(name = "loginService")
	private LoginService loginService;

	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, LoginForm loginForm, Model model) {
		try {
			Person person = loginService.doLogin(loginForm.getUsername(),
					loginForm.getPassword());
			initSession(request,person);
			return "redirect:/system/home.htm";
		} catch (PasswordNotCorrectException e) {
			model.addAttribute("errorMessage", e.getMessage());
		} catch (PersonNotFoundException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		return HOME;
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request){
		clear(request);
		return "redirect:/system/home.htm";
	}

	@RequestMapping(value = "/home")
	public String home(HttpServletRequest request, Model model) {
		return HOME;
	}
}
