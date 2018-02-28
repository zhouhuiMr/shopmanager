package com.object;

public enum resultEnum {
	
	/**---------------------------**/
	/**-- basic message start 0 --**/
	/**---------------------------**/
	SUCCESS("0000", "成功"),
	FAILED("0001","失败"),
	TIMEOUT("0002","等待超时"),
	CODEERROR("0003","验证码错误"),
	

	/**---------------------------**/
	/**-- user message start 1  --**/
	/**---------------------------**/
	LOGINERROR("1001","用户名或者密码错误"),
	LOGINTIMEOUT("1002","登录超时"),
	
	ERROR("-1","服务器错误");

	private String code;
	private String msg;
	
	private resultEnum(String code,String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
