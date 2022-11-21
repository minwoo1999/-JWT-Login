package com.jang.mtg.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jang.mtg.model.User;







@Mapper
public interface UserMapper {
	
	User getUser(String userId);
	
	int updateUser(User user);
	int insertUser(User user);
	User findId(@Param("name")String name,@Param("email")String email);
	User findPass(@Param("id")String id,@Param("email")String email);
	int updatePass(User user);

}
