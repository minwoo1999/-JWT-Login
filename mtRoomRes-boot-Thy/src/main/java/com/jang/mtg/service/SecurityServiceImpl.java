package com.jang.mtg.service;



import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class SecurityServiceImpl implements SecurityService {

	
	private static String secretKey="4C8kum4LxyKWYLM78sKdXrzb8jDCFyfX";
	
	@Override
	public String createToken(String subject, long ttlMillis) {
		// TODO Auto-generated method stub
		
		System.out.println("들어왔당");
		
		if(ttlMillis ==0) {
			throw new RuntimeException("토큰 만료기간은 0이상 이어야합니다.");
		}
		
		//HS256방식으로 암호화 방식 설정
		System.out.println("여기");
		SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
		byte[]apiKeySeBytes=DatatypeConverter.parseBase64Binary(secretKey);
		Key signingKey=new SecretKeySpec(apiKeySeBytes, signatureAlgorithm.getJcaName());
		
		JwtBuilder builder =Jwts.builder()
				.setSubject(subject)
				.signWith(signatureAlgorithm, signingKey);
		
		long nowMillis=System.currentTimeMillis();
		builder.setExpiration(new Date(nowMillis+ttlMillis));
		return builder.compact();
	}
	
	//token을 식별

	@Override
	public String getSubject(String token) {
		// TODO Auto-generated method stub
		
		Claims claims=Jwts.parser()
				.setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
				.parseClaimsJws(token).getBody();
		
		
		return claims.getSubject();
	}
	
	

}
