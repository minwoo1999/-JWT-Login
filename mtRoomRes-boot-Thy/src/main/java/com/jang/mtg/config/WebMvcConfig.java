package com.jang.mtg.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jang.mtg.interceptor.LoggerInterceptor;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer   {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/image/**").addResourceLocations("file:///C:/upload/");
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(new LoggerInterceptor())
		.addPathPatterns("/*")
		.excludePathPatterns("/join","/login","/token");
	}

}
