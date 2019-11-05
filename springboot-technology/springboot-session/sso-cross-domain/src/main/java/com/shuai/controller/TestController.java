package com.shuai.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shuai.commons.utils.CookieUtils;

@Controller
public class TestController {
	
	@RequestMapping("/test")
	public String test(HttpServletRequest request, HttpServletResponse response){
		
		String cookieName="custom_global_session_id";
		String encodeString="UTF-8";
		
		String cookieValue = CookieUtils.getCookieValue(request, cookieName, encodeString);
		
		if(null == cookieValue || "".equals(cookieValue.trim())){
			System.out.println("无cookie，生成新的cookie数据");
			cookieValue = UUID.randomUUID().toString();
		}
		
		// 根据cookieValue访问数据存储，获取客户端数据。
		
		CookieUtils.setCookie(request, response, cookieName, cookieValue, 0, encodeString);
		
		return "/ok.jsp";
	}
	
}
