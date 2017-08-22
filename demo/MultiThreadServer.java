import java.net.*;
import java.io.*;

public class MultiThreadServer implements Runnable{
	private Socket client;   

	public MultiThreadServer(Socket c) {
		client = c;
	}
	
	@Override
	public void run() {
		try {
			BufferedWriter w
					= new BufferedWriter(
							new OutputStreamWriter(client.getOutputStream()));
			w.write("服务器已收到.......");
			w.flush();
			w.close();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		ServerSocket ss= null;
		try {
			ss = new ServerSocket(1888);
			int n = 0;
			while(true) {
				Socket c = ss.accept();
				System.out.println("第"+ (++n) + "位访问的客户");
				new Thread(new MultiThreadServer(c)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
