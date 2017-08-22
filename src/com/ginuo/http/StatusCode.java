package com.ginuo.http;

//RFC定义的状态码太多了。。。。这里暂时用到这几个
public enum StatusCode {
	_200("200 OK"), 
	_400("400 Bad Request"), 
	_404("404 Not Found"), 
	_501("501 Not Implemented");     //方法未实现


	private final String status;

	StatusCode(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return status;
	}
}
