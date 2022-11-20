package com.jang.mtg.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class LoggerInterceptor implements HandlerInterceptor {
	
	private Logger logger= LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
		
		Object userId=request.getSession().getAttribute("userId");
		
		if(userId==null) {
			
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}else {
			return true;
		}
	}

}
