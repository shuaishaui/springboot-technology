package com.shuai.commons;

/**
 * 发送给客户端的数据对象。
 * 商业开发中，一般除特殊请求外，大多数的响应数据都是一个统一类型的数据。
 * 统一数据有统一的处理方式。便于开发和维护。
 */
public class JWTResponseData {

	private Integer code;// 返回码，类似HTTP响应码。如：200成功，500服务器错误，404资源不存在等。
	
	private Object data;// 业务数据
	
	private String msg;// 返回描述
	
	private String token;// 身份标识， JWT生成的令牌。

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
