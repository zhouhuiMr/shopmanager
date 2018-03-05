package com.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.config.initConfig;
import com.manager.model.managerModel;
import com.object.managers;
import com.object.resultEnum;
import com.object.resultObject;

@Controller
@RequestMapping("/action")
public class managerController {
	private static final Object loginLock = new Object();
	
	@Autowired
	private managerModel manager;
	
	@Autowired
	private HttpServletRequest req;

	@Autowired
	private HttpServletResponse res;
	
	@RequestMapping(path = "/login" ,method = RequestMethod.POST)
	@ResponseBody
	public resultObject managerLogin(
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("authcode") String authcode
			) {
		resultObject json = new resultObject();
		String code = "";
		synchronized (loginLock) {
			code = (String) req.getSession().getAttribute(initConfig.picAuthCodeName);
			req.getSession().removeAttribute(initConfig.picAuthCodeName);
		}
		if(authcode.equals(code)) {
			managers m = new managers();
			m.setUsername(username);
			m.setPassword(password);
			json = manager.managerLogin(m, res);
		}else {
			json.setResult(resultEnum.CODEERROR);
			json.setData("");
		}
		return json;
	}
}
