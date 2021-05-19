package com.howtostudyit.jbc.controller;

import java.security.Principal;

import javax.persistence.CascadeType;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.howtostudyit.jbc.entity.Blog;
import com.howtostudyit.jbc.service.BlogService;
import com.howtostudyit.jbc.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	@ModelAttribute("blog")
	public Blog constructBlog() {
		return new Blog();
	}
	
	@RequestMapping("/account")
	public String account(Model model, Principal principal) {
		String name = principal.getName(); // the name in session
		model.addAttribute("user", this.userService.findOneWithBlogs(name));
		return "account";
	}

	@RequestMapping(value="/account", method=RequestMethod.POST)
	public String doAddBlog(Model model, @Valid @ModelAttribute("blog") Blog blog, BindingResult result, Principal principal) {
		if(result.hasErrors()) {
			return account(model, principal);
		}
		String name = principal.getName();
		blogService.save(blog, name);
		return "redirect:/account.html";
	}
	
	  
	@RequestMapping("/blog/remove/{id}")
	public String removeBlog(@PathVariable int id) {
		
		// ConstraintViolationException occurs when there is items. -> cascade=CascadeType.REMOVE
		
//		// users can delete administrator's blogs: security issue
//		blogService.delete(id); 
		
		// Preventing users from deleting administrator's blogs
		Blog blog = this.blogService.findOne(id);
		blogService.delete(blog);
		return "redirect:/account.html";
	}
}
