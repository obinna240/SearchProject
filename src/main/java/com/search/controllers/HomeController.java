package com.search.controllers;

//import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.search.query.QueryObject;

@Controller
@RequestMapping({"/"})
public class HomeController
{
	@RequestMapping(method=RequestMethod.GET)
	public String home(Model model)//public String home(Principal principal)
	{
		//return principal != null ? "/homeSignedIn" : "/homeNotSignedIn";
		model.addAttribute("queryObject", new QueryObject());
		return "home";
	}
	
	@PostMapping
	public String doSearch(QueryObject queryObject, Model model)
	{
		//do search
		model.addAttribute("queryObject", new QueryObject());
		model.addAttribute("r","result");
		return "home";
	}
}
