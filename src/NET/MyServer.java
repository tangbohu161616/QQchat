/**
 *TODO
 *ÏÂÎç2:20:41
 *MyServer.java
 *wangzhigang
 */
package NET;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 *
 */
public class MyServer {

	public static EMAP<String,PrintStream> clients = new EMAP<>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket ss = new ServerSocket(30000);
			while (true) {
				Socket s = ss.accept();
				new Thread(new ServerThread(s)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
