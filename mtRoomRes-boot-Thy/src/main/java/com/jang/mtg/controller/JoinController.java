package com.jang.mtg.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jang.doc.utils.AES256Util;
import com.jang.mtg.model.User;
import com.jang.mtg.service.UserService;





@Controller
public class JoinController {

	@Autowired 
	private UserService userService;

	@RequestMapping(value = "/join", method = RequestMethod.GET) // ?öå?õêÍ∞??ûÖ ?òÅ?ó≠ form Ï∂úÎ†•
	public String userJoinForm(Model model) {
		model.addAttribute("user", new User());
		return "joinForm";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST) // ?ûÖ?†•?èº ?Ç¥?ö© ???û•
	public String onSubmit(@Valid User user, BindingResult result, Model model) throws Exception {

		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "joinForm";
		}
		//passwd ?ïî?ò∏?ôî ?ã®Î∞©Ìñ• ?ïî?ò∏?ôî
		
		BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		String hashPass=passwordEncoder.encode(user.getPass());
		user.setPass(hashPass);
		
		//?Éù?ÖÑ?õî?ùº ?ïî?ò∏?ôî aes256 ?ñëÎ∞©Ìñ• ?ïî?ò∏?ôî
		
		Path filePath=Paths.get("C://jj/key3.txt");
		String key=Files.readString(filePath);
		
		AES256Util aes256=new AES256Util(key);
		
		String hashBirthday=aes256.aesEncode(user.getBirthday());
		user.setBirthday(hashBirthday);
		

		if (this.userService.insertUser(user) != 0) {
			user.setPass("");
			model.addAttribute("user", user);
			model.addAttribute("msgCode", "?ì±Î°ùÎêò?óà?äµ?ãà?ã§. Î°úÍ∑∏?ù∏?ïò?ó¨ Ï£ºÏã≠?ãú?ò§.");// ?ì±Î°ùÏÑ±Í≥?
			return "login";
		} else {
			model.addAttribute("msgCode", "?ì±Î°? ?ã§?å®?ïò???äµ?ãà?ã§. ?ã§?ãú ?ãú?èÑ?ïò?ó¨ Ï£ºÏã≠?ãú?ò§.");// ?ì±Î°ùÏã§?å®
			return "joinForm";
		}

	}

	@RequestMapping(value = "/idCheck", method = RequestMethod.GET) // IDÏ§ëÎ≥µÏ≤¥ÌÅ¨
	@ResponseBody
	public String idCheck(HttpServletRequest request) {
		
		System.out.println("1");

		String userId = request.getParameter("userId");

		Gson gson = new Gson();
		JsonObject object = new JsonObject();

		User loginUser = this.userService.getUser(userId);

		if (loginUser != null) {
			object.addProperty("msg", "false");
			return gson.toJson(object).toString();
		} else {
			object.addProperty("msg", "true");
			return gson.toJson(object).toString();
		}
	}
	


}
