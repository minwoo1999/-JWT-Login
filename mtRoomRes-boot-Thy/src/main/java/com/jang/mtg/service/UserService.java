package com.jang.mtg.service;

import com.jang.mtg.model.User;

public interface UserService {
	User getUser(String userId);
	
	int updateUser(User user);
	int insertUser(User user);
	
	User findId(String name,String email);
	User findPass(String id,String email);
	int updatePass(User user);

	
}
