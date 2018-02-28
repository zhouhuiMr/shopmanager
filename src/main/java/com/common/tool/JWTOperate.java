package com.common.tool;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.config.initConfig;

public class JWTOperate {
	private final static String ISSURE = "shop";

	/**
	 * @param LinkedHashMap<String,String> 
	 * token保存的信息
	 * 
	 * @see https://github.com/auth0/java-jwt
	 * 
	 * @return token
	 */
	public static String getToken(Map<String, String> option) {
		String tokenKey = initConfig.TOKENKEY;
		Algorithm algorithm;
		String token = "";
		try {
			algorithm = Algorithm.HMAC256(tokenKey);
			Builder build = JWT.create().withIssuer(ISSURE);
			Iterator<String> optionKey = option.keySet().iterator();
			while (optionKey.hasNext()) {
				String key = optionKey.next();
				String value = option.get(key);
				build = build.withClaim(key, value);
			}
			token = build.sign(algorithm);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return token;
	}

	/**
	 * @param String token
	 * 
	 * @return DecodedJWT
	 * */
	public static DecodedJWT setVerify(String token) {
		String tokenKey = initConfig.TOKENKEY;
		DecodedJWT t = null;
		try {
			Algorithm algorithm = Algorithm.HMAC256(tokenKey);
			JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSURE).build();
			t = verifier.verify(token);
		} catch (Exception e) {
			
		}
		return t;
	}
	
	/**
	 * @param String 日期格式'yyyyMMdd-HH:mm:ss'
	 * 
	 * 判断输入日期是否小于当前日期的天数
	 * 
	 * @return boolean
	 * */
	public static boolean isTimeOut(String startTime) {
		LocalDate endTime = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
		LocalDate start = LocalDateTime.parse(startTime,formatter).toLocalDate();
		int day = Period.between(start, endTime).getDays();
		if(day >= Integer.valueOf(initConfig.USERLOGINEXPIRES)) {
			return true;
		}else {
			return false;
		}
	}
}
