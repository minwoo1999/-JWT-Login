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
		
		System.out.println("���Դ�");
		
		if(ttlMillis ==0) {
			throw new RuntimeException("��ū ����Ⱓ�� 0�̻� �̾���մϴ�.");
		}
		
		//HS256������� ��ȣȭ ��� ����
		System.out.println("����");
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
	
	//token�� �ĺ�

	@Override
	public String getSubject(String token) {
		// TODO Auto-generated method stub
		
		Claims claims=Jwts.parser()
				.setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
				.parseClaimsJws(token).getBody();
		
		
		return claims.getSubject();
	}
	
	

}
