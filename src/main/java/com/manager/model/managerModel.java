package com.manager.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.tool.JWTOperate;
import com.common.tool.SHA256Str;
import com.config.initConfig;
import com.data.mapper.managersDao;
import com.object.managers;
import com.object.resultEnum;
import com.object.resultObject;

@Service
public class managerModel {
	
	@Autowired
	private managersDao managersDao;
	
	public resultObject managerLogin(managers managers,HttpServletResponse res) {
		resultObject resultObject = new resultObject();
		String encodePassword = SHA256Str.encodeStrBySHA256(managers.getPassword());
		managers m =managersDao.managerLogin(managers.getUsername(), encodePassword);
		if(m == null) {
			resultObject.setResult(resultEnum.LOGINERROR);
			resultObject.setData("");
		}else {
			HashMap<String, String> option = TokenOption(m);
			String token = JWTOperate.getToken(option);
			setCookie(res, "token", token);
			setCookie(res, "username", urlEncode(m.getUsername()));
			setCookie(res, "localtime", localDateTime());
			resultObject.setResult(resultEnum.SUCCESS);
			resultObject.setData("");
		}
		return resultObject;
	}
	
	/**
	 * @param clientUser 用户的信息
	 * 
	 * 生成token字符串
	 * 
	 * @return HashMap<String, String> 生成token的参数
	 * */
	private HashMap<String, String> TokenOption(managers user){
		HashMap<String, String> option = new HashMap<String, String>();
		option.put("clientID", user.getId()+"");
		option.put("clientName", user.getUsername()+"");
		option.put("loginStart", localDateTime());
		option.put("expires", initConfig.USERLOGINEXPIRES);
		return option;
	}
	
	/**
	 * @param HttpServletResponse 请求的返回值
	 * @param String cookie的名称
	 * @param String cookie的值
	 * 
	 * 将有效数据添加到请求的返回中
	 * 
	 * */
	private void setCookie(HttpServletResponse res,String name,String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		res.addCookie(cookie);
	}
	
	/**
	 * @param String
	 * 
	 * 将字符串进行URLEncode编码
	 * 
	 * @return String
	 * */
	private String urlEncode(String str) {
		String encodeStr = "";
		try {
			encodeStr =  URLEncoder.encode(str, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeStr;
	}
	
	/**
	 * @return 返回指定格式的时间
	 * */
	private String localDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
		return formatter.format(LocalDateTime.now());
	}
}
