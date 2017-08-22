package com.ginuo.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;


public class Client {
	//������
	static final String REQ_LINE = "GET / HTTP/1.1";
	//Host��
	static final String H_HOST = "localhost:8080";
	//Accept:
	static final String H_ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	//User-Agent:
	static final String H_USER_AGENT = "MyHTTPClient/1.0";
	
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 8080);
			BufferedWriter writer = 
					new BufferedWriter(
							new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader reader = 
					new BufferedReader(
							new InputStreamReader(socket.getInputStream()));
			writer.write(REQ_LINE + "\r\n\r\n");
			writer.write(H_HOST + "\r\n");
			writer.write(H_ACCEPT+ "\r\n");
			writer.write(H_USER_AGENT + "\r\n");
			writer.write("Date: "+ new Date() + "\r\n");
			writer.flush();
			//注意：不能在此关闭writer！！，不然会异常
			String lineOfResponse;
			//读取并打印服务器返回的数据
			while((lineOfResponse = reader.readLine()) != null) {
				System.out.println(lineOfResponse);
			}
			writer.close();
			reader.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
