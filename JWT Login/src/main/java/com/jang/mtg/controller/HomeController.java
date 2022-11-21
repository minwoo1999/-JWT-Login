package com.jang.mtg.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Base64;
import java.util.Date;

import com.jang.mtg.model.User;
import com.jang.mtg.service.SecurityService;

import io.jsonwebtoken.Claims;



@RestController
public class HomeController {
	
	
	@Autowired
	SecurityService securityService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	//토큰 생성
	@GetMapping("token")
	public Map<String,Object> generateToken(@RequestParam String subject){
		System.out.println(subject);
		String token=securityService.createToken(subject);
		System.out.println(token);
		Claims claims = securityService.parseJwtToken("Bearer "+ token); // 토큰 검증
		
		
		Map<String,Object> map =new HashMap<>();
		map.put("userid", subject);
		map.put("token", token);
		map.put("expire",  claims.getExpiration().toString());
		map.put("IssuedAt", claims.getIssuedAt().toString());
		System.out.println(map);
		return map;
		
	}
	//token 검증
	@GetMapping("security/get/subject")
	public Map<String,Object> getSubject(@RequestHeader(value="Authorization")String token,Model model) {
		Claims subject =securityService.parseJwtToken(token);
		
		 Map<String,Object> map=new HashMap<>();
		 map.put("code", 400);
		 map.put("msg", "TokenSuccess");
		 
		 return map;
	
	}
	
	
	
	
	
	
}
