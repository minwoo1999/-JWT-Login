package com.jang.mtg.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jang.mtg.mapper.UserMapper;
import com.jang.mtg.model.User;






@Service(value="userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper; //?ù∏?Ñ∞?éò?ù¥?ä§?? ?ùòÏ°¥Í?Í≥? ?Ñ§?†ï

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		return userMapper.getUser(userId);
	}

	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.updateUser(user);
	}

	@Override
	public int insertUser(User user) {
		// TODO Auto-generated method stub
		return userMapper.insertUser(user);
	}

	@Override
	public User findId(String name, String email) {
		// TODO Auto-generated method stub
		return userMapper.findId(name, email);
	}

	@Override
	public User findPass(String id, String email) {
		// TODO Auto-generated method stub
		return userMapper.findPass(id, email);
	}

	@Override
	public int updatePass(User user) {
		// TODO Auto-generated method stub
		return userMapper.updatePass(user);
	}
	
	


}
