package com.howtostudyit.jbc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.howtostudyit.jbc.entity.User;
import com.howtostudyit.jbc.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {
	@Autowired
	private UserService userService;
	
	// <form:form commandName="user">
	@ModelAttribute("user")
	public User constructUser() {
		return new User();
	}
	
	@RequestMapping
	public String showRegister() {
		return "user-register";
	}
	
//	// No validation
//	@RequestMapping(method=RequestMethod.POST)
//	public String doRegister(@Valid @ModelAttribute("user") User user) {
//		
//		userService.save(user);
//		return "redirect:/register.html?success=true";	
//	}
	
	// Validation
	@RequestMapping(method=RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			return "user-register";
		}
		userService.save(user);
		
		// send some data to view using RedirectAttributes
		redirectAttributes.addFlashAttribute("success", true);
		
		return "redirect:/register.html";
		
		
	}
	
	@RequestMapping("/available")
	@ResponseBody
	public String available(@RequestParam String username) {
		Boolean available = userService.findOne(username) == null;
		return available.toString();
	}
}
