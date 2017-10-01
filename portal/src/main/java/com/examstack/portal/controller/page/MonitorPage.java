package com.examstack.portal.controller.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MonitorPage {

	@RequestMapping(value = { "users/online" }, method = RequestMethod.GET)
	public String showTopMenuPage(Model model, HttpServletRequest request)
	{
		ServletContext context = request.getSession().getServletContext();
		
		model.addAttribute("lineCount",context.getAttribute("lineCount")); 
		HashMap<String,String> users = new HashMap<String,String>();
	    HashSet<HttpSession> sessionSet=(HashSet<HttpSession>) context.getAttribute("sessionSet");  
		
	    for(HttpSession s1 : sessionSet)
	    {
	    	String userName = s1.getAttribute("username") == null? "游客": s1.getAttribute("username").toString();
	    	
	    	users.put(s1.getId(), userName);
	    }
	    
	    model.addAttribute("users", users);
	    
	    return "users-online";
	}
}
