package com.ginuo.http;

import java.io.IOException;
import java.net.ServerSocket;

public class MyWebServer {

	private static final int DEFAULT_PORT = 8080;

	private int port;

	public static void main(String args[]) {
		try {
			MyWebServer webServer = new MyWebServer();
			if(args.length > 0) {      //从命令行参数获取端口号
				webServer.port = Integer.parseInt(args[0]);
			}else {     
				webServer.port = DEFAULT_PORT;
			}
			webServer.start();
		} catch (Exception e) {
			System.err.println("服务器启动失败，可能是端口被占用");
		}
	}

	//用于创建ServerSocket监听请求
	public void start() throws IOException {
		@SuppressWarnings("resource")
		ServerSocket s = new ServerSocket(port);
		System.out.println("服务器正在监听端口 " + port);
		while (true) {
			new Thread(new RequestHandler(s.accept())).start();
		}
	}

}
