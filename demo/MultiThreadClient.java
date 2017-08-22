import java.net.*;
import java.io.*;

public class MultiThreadClient implements Runnable{
	public static void main(String[] args) {
		int n = 6;     //创建6个模拟client线程
		while(n-- > 0) {
			MultiThreadClient mu = new MultiThreadClient();
			Thread t = new Thread(mu);
			t.start();
		}
	}

	@Override
	public void run() {
		Socket s = null;
		try {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s = new Socket("127.0.0.1", 1888);
			BufferedReader r 
					= new BufferedReader(new InputStreamReader(s.getInputStream()));
			System.out.println(Thread.currentThread().getName()+r.readLine());
			r.close();
			s.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
