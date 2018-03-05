package com.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.common.tool.JWTOperate;

@WebFilter(filterName = "loginfilter",urlPatterns= {"/views/*","/action/manager/*"})
public class actionFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String token = getTokenByRequest(req);
		DecodedJWT t = JWTOperate.setVerify(token);
		if(t != null) {
			String startTime = t.getClaim("loginStart").asString();
			if(JWTOperate.isTimeOut(startTime)) {
				res.sendRedirect("../index.html");
			}else {
				chain.doFilter(request, response);
			}
		}else {
			res.sendRedirect("../index.html");
		}
	}

	@Override
	public void destroy() {
		
	}
	
	private String getTokenByRequest(HttpServletRequest req) {
		String token = "";
		Cookie cookies[] = req.getCookies();
		if(cookies != null) {
			for(Cookie c:cookies) {
				if("token".equals(c.getName())) {
					token = c.getValue();
				}
			}
		}
		return token;
	}
}
