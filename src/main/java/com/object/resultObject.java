package com.object;

import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class resultObject {
	private String code = "";
	private Object data = null;
	private String message = "";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setResult(resultEnum resultEnum) {
		this.code = resultEnum.getCode();
		this.message = resultEnum.getMsg();
	}
}
