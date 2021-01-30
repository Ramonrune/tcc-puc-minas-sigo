package com.sigo.api.token;

import java.io.IOException;

import org.springframework.security.jwt.JwtHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sigo.api.model.JsonWebToken;

public class JwtTokenDecoder {
	
	private JwtTokenDecoder() {
		
	}

	public static JsonWebToken decode(String token) {
		org.springframework.security.jwt.Jwt decode = JwtHelper.decode(token.replace("Bearer ", ""));

		ObjectMapper objectMapper = new ObjectMapper();

		JsonWebToken jsonWebToken = null;
		try {
			jsonWebToken = objectMapper.readValue(decode.getClaims(), JsonWebToken.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		return jsonWebToken;
	}
}
