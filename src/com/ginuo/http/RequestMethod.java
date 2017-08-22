package com.ginuo.http;

//暂时只实现了标准中的GET、HEAD方法
public enum RequestMethod {
	GET("GET"),
	HEAD("HEAD"), 
	POST("POST"), 
	PUT("PUT"), 
	DELETE("DELETE"), 
	TRACE("TRACE"),
	CONNECT("CONNECT"),
	OTHER("UNRECOGNIZED");    //请求行中无法识别的方法
	

	@SuppressWarnings("unused")
	private final String method;
	
	RequestMethod(String method) {
		this.method = method;
	}
	
}
