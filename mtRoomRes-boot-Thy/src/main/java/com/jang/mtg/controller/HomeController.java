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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jang.mtg.model.User;
import com.jang.mtg.service.SecurityService;



@Controller
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
	
	//토큰 발행
	@GetMapping("/token")
	public String generateToken(@RequestParam String subject,Model model){
		System.out.println(subject);
		String token=securityService.createToken(subject, 1000*60*60);
		Map<String,Object> map =new HashMap<>();
		map.put("userid", subject);
		map.put("token", token);
		model.addAttribute("user", new User());
		System.out.println(map);
		return "login";
		
	}
	//token 요청(식별)
	@GetMapping("security/get/subject")
	public String getSubject(@RequestParam String token,Model model) {
		String subject =securityService.getSubject(token);
		System.out.println(subject);
		model.addAttribute("user", new User());
		return "login";
	
	}
	
	
}
