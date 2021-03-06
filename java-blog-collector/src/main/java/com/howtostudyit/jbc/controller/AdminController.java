package com.howtostudyit.jbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.howtostudyit.jbc.service.UserService;

@Controller
@RequestMapping("/users")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping
	public String index(Model model) {
		model.addAttribute("users", userService.findAll());
		return "users"; // definition name in general.xml for tiles
	}	

	// Solving LazyInitializationException
	@RequestMapping("/{id}")
	public String detail(Model model, @PathVariable int id) {	
		model.addAttribute("user", this.userService.findOneWithBlogs(id));
		return "user-detail";
		
	}
	
	/* LazyInitializationException */
//	@RequestMapping("/{id}")
//	public String detail(Model model, @PathVariable int id) {
//		model.addAttribute("user", userService.findOne(id));
//		return "user-detail";	
//	}
	
	
	@RequestMapping("/remove/{id}")
	public String removeUser(@PathVariable int id) {
		userService.delete(id);
		return "redirect:/users.html";
	}
}
