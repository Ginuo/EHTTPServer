package com.ginuo.http;

import java.net.Socket;

public class RequestHandler implements Runnable{

	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			//根据socket的输入流（相当于根据请求头）构造HttpRequest对象
			HttpRequest req = new HttpRequest(socket.getInputStream());
			HttpResponse res = new HttpResponse(req);  //构造响应对象
			res.write(socket.getOutputStream());        //写入流，发送给客户端
			socket.close();
		} catch (Exception e) {
			System.err.println("处理请求失败：RequestHandle:run");
			e.printStackTrace();
		}
	}
}
