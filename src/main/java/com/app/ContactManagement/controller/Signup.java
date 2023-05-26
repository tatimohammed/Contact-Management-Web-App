package com.app.ContactManagement.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.ContactManagement.model.User;
import com.app.ContactManagement.service.UserServiceImpl;

@Controller
public class Signup {
	
	@Autowired
	private UserServiceImpl userServiceImpl;

	@GetMapping("/signup")
	public String showSingup(HttpSession session) {
		
		return "signupPage";
	}

	@GetMapping("/createUser")
	public String addUser(@RequestParam(name = "name") String name, @RequestParam(name = "username") String username,
			@RequestParam(name = "password") String passwd, HttpSession session) {

		User u = new User(name, username, passwd);
		
		if (!userServiceImpl.isExist(username)) {
			userServiceImpl.addUser(u);
			session.setAttribute("msg", "Account Created Sucessfully :)");
			
		}
		else {
			session.setAttribute("msg", "Account Already Exist!");
		}
		
		return "redirect:/signup";
	}
}
