package com.shuai.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	
	@RequestMapping("/test")
	public String test(HttpServletRequest request, HttpServletResponse response){
		
		Object testAttrValue = request.getSession().getAttribute("testAttrName");
		
		if(null == testAttrValue){
			request.getSession().setAttribute("testAttrName", "testAttrValue");
		}
		
		System.out.println("80: " + testAttrValue);
		
		return "/ok.jsp";
	}
	
}
