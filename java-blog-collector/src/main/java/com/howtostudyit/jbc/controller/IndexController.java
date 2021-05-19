package com.howtostudyit.jbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.howtostudyit.jbc.service.ItemService;

//import com.howtostudyit.jbc.service.ItemService;

@Controller
public class IndexController {
	@Autowired
	private ItemService itemService;

	@RequestMapping({ "/index" })
	public String index(Model model) {
		model.addAttribute("items", this.itemService.getItems());
		return "index"; // definition name in general.xml for tiles
	}

	// @RequestMapping({ "/index" })
	// public String index() {
	// return "index"; // definition name in general.xml for tiles
	//
	// }

	// @RequestMapping({ "/index" })
	// public String index() {
	// return "WEB-INF/jsp/index.jsp"; // before tiles
	//
	// }

}
