package com.ginuo.http;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum ContentType {
	CSS("css"), 
	GIF("gif"), 
	HTML("html"), 
	JPG("jpg"), 
	JPEG("jpeg"), 
	PNG("png"), 
	TXT("txt"), 
	JAVA("java");
	
	@SuppressWarnings("unused")
	private final String ext;

	ContentType(String ext) {
		this.ext = ext;
	}
	
	//重写toString，用于返回标准的MIME类型字符串
	@Override
	public String toString() {
		switch (this) {
			case HTML:
				return "Content-Type: text/html";
			case CSS:
				return "Content-Type: text/css";
			case GIF:
				return "Content-Type: image/gif";
			case JPG:
			case JPEG:
				return "Content-Type: image/jpeg";
			case PNG:
				return "Content-Type: image/png";
			case TXT:
				return "Content-type: text/plain";
			case JAVA:
				return "Content-type: text/plain";
			default:
				return null;
		}
	}
}



public class HttpResponse {

	private List<String> headers = new ArrayList<String>();
	private byte[] responseBody;
	/*
	 * @param req 接收到的HTTP请求对象
	 * 因为需要根据请求内容来构造HttpResponse对象用于返回客户，
	 * 所以直接用它作为构造函数的参数
	 * 
	 * 根据请求方法和请求URI构造Response，能够通过服务器获取静态的文件
	 *     1. 如果url是一个路径，则列出路径下所有的文件
	 *     2. 如果url指定了哪个文件，直接返回该文件
	 */
	public HttpResponse(HttpRequest req) throws IOException {
		switch (req.getMethod()) {
			case HEAD:   //如果请求方法时Head，只需要返回Headers即可
				setHeaders(StatusCode._200);
				break;
			case GET:
				try {
					setHeaders(StatusCode._200);
					File curFile = new File("." + req.getUrl());
					
					System.out.println(curFile.getAbsolutePath());
					
					if (curFile.isDirectory()) {   //若是目录，则打印出目录下的文件列表
						headers.add(ContentType.HTML.toString());
						StringBuilder result = new StringBuilder("<html><head><meta charset=\"utf-8\"/><title>");
						result.append("文件列表");
						result.append("</title></head><body><h1>文件列表： ");
						result.append(req.getUrl());
						result.append("</h1><pre>");

						File[] files = curFile.listFiles();
						for (File subfile : files) {
							result.append(" <a href=\"" + subfile.getPath() + "\">" + subfile.getPath() + "</a>\n");
						}
						result.append("</pre></body></html>");
						setBody(result.toString());
					} else if (curFile.exists()) {     //若指定文件存在，返回文件
						setContentType(req.getUrl(), headers);
						setBody(getBytes(curFile));
					} else {                        //404 NOT FOUND
						System.err.println("File not Found" + req.getUrl());
						
						setHeaders(StatusCode._404);  
						setBody(StatusCode._404.toString());
					}
				} catch (Exception e) {
					setHeaders(StatusCode._400);     //Bad Request
					setBody(StatusCode._400.toString());
				}
				break;
			default:
				setHeaders(StatusCode._501);
				setBody(StatusCode._501.toString());
		}

	}

	//因为是网络传输，需要将文件流转换成字节流
	private byte[] getBytes(File file) throws IOException {
		int length = (int) file.length();
		byte[] array = new byte[length];
		InputStream in = new FileInputStream(file);
		int offset = 0;
		while (offset < length) {
			int count = in.read(array, offset, (length - offset));
			offset += count;
		}
		in.close();
		return array;
	}

	//添加简单统一的首部，其中status需要视情况确定
	private void setHeaders(StatusCode status) {
		headers.add("HTTP/1.1" + " " + status.toString());
		headers.add("Connection: close");      //非持久连接...
		headers.add("Server: MyWebServer");
		headers.add("Date: "+ new Date());
	}
	
	
	private void setBody(String response) {
		responseBody = response.getBytes();
	}

	private void setBody(byte[] response) {
		responseBody = response;
	}
	
	//写入headers和body
	public void write(OutputStream os) throws IOException {
		DataOutputStream output = new DataOutputStream(os);
		for (String header : headers) {
			output.writeBytes(header + "\r\n");  //\r\n标准定的，我能怎么办ε=(´ο｀*)))
			                                     //不过即使不加\r，浏览器也能识别
		}
		output.writeBytes("\r\n");    //响应行与headers之间有一空行
		if (responseBody != null) {
			output.write(responseBody);
		}
		output.writeBytes("\r\n");
		output.flush();
	}
	
	//根据URL后缀设置ContentType首部，并添加到headers中
	private void setContentType(String url, List<String> list) {
		try {
			String ext = url.substring(url.indexOf(".") + 1);
			list.add(ContentType.valueOf(ext).toString());
		} catch (Exception e) {
			System.err.println("枚举中未定义的MIME类型： " + e);
		}
	}
	
//	//测试后删除
//	public static void main(String[] args) {
//		HttpRequest r = new HttpRequest();
//		r.setMethod(Method.GET);
//		r.setUri("/");
//		try {
//			HttpResponse respon = new HttpResponse(r);
//			respon.setHeaders(StatusCode._200);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}