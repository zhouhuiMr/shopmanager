package com.authcode.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.tool.PicAuthCode;
import com.config.initConfig;

@Controller
@RequestMapping(path="/action/authcode")
public class picAuthCodeController {
	@Autowired
	HttpServletRequest req;
	
	@Autowired
	HttpServletResponse res;
	
	@RequestMapping(path="/picauthcode",method = RequestMethod.GET)
	public void createAuthCode() {
		String authcode = PicAuthCode.getAuthCode().toLowerCase();
		req.getSession().setAttribute(initConfig.picAuthCodeName, authcode);
		try {
			PicAuthCode.outputImage(120, 38, res.getOutputStream(), authcode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
