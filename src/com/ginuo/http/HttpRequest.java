package com.ginuo.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
	private List<String> headers = new ArrayList<String>();
	private RequestMethod method;     //请求行中的方法、URL、Version
	private String url;
	private String version;

	//根据客户端的请求（InputStream）构造出HttpRequest
	public HttpRequest(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String str = reader.readLine();
		parseRequestLine(str);
		while (!str.equals("")) {
			str = reader.readLine();
			parseRequestHeader(str);
		}
	}

	//对请求行进行解析，分离出请求方法、URL、Http版本
	private void parseRequestLine(String str) {
		//System.out.println(str);	
		String[] split = str.split("\\s+");  //三部分之间是用空字符分隔的
		try {
			setMethod(RequestMethod.valueOf(split[0]));
		} catch (Exception e) {
			setMethod(RequestMethod.OTHER);
		}
		url = split[1];
		System.out.println("uri: "+url);
		version = split[2];
		System.out.println("version: "+version);
	}

	/*
	 * 添加一行首部到headers
	 */
	private void parseRequestHeader(String str) {
		System.out.println(str);
		headers.add(str);
	}

	public RequestMethod getMethod() {
		return method;
	}

	public void setMethod(RequestMethod method) {
		this.method = method;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
//	//测试后删除
//	public HttpRequest() {
//		
//	}
//	
//	//测试后删除
//	public void setMethod(Method method) {
//		this.method = method;
//	}
//	
//	//测试后删除
//	public void setUri(String uri) {
//		this.uri = uri;
//	}
//	
//	//测试后删除
//	public static void main(String[] args) {
//		HttpRequest request = new HttpRequest();
//		//request.parseRequestLine("GET http://www.baidu.com HTTP/1.1");
//		request.parseRequestHeader("Connection:  Keep-Alive");
//		
//	}
}
