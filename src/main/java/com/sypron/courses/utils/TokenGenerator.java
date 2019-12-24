package com.sypron.courses.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


@Component
public class TokenGenerator {

	@Value("${token.duration}")
	private String days;

	@Value("${token.encodedkey}")
	private String encodedKey;

	public String getToken(String roles, String userId) {

		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);

		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, Integer.valueOf(days));
		Date date = c.getTime();

		String loginSessionId = UUID.randomUUID().toString();

		String s = Jwts.builder().setSubject(String.valueOf(userId)).claim("role", roles).setExpiration(date)
				.signWith(SignatureAlgorithm.HS512, originalKey).setId(loginSessionId).compact();

		return "Bearer " + s;
	}
}
