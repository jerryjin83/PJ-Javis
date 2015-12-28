package com.jerry.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jerry.bean.form.LoginForm;
import com.jerry.bean.modal.Person;
import com.jerry.common.exceptions.PasswordNotCorrectException;
import com.jerry.common.exceptions.PersonNotFoundException;
import com.jerry.service.LoginService;

@Controller
@RequestMapping(value = "/system")
public class SystemAction extends AbstractController {

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
