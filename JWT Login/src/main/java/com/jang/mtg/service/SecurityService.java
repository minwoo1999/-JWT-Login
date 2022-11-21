package com.jang.mtg.service;

import io.jsonwebtoken.Claims;

public interface SecurityService {

	String createToken(String subject);
	Claims parseJwtToken(String token);
	String BearerRemove(String token);
}
