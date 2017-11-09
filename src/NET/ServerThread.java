/**
 *TODO
 *下午2:24:24
 *ServerThread.java
 *wangzhigang
 */
package NET;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @author 
 *
 */
public class ServerThread implements Runnable {

	Socket s = null;
	BufferedReader br = null;
	
	public ServerThread(Socket s){
		this.s = s;
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String content = null;
		PrintStream ps =null;
		try {
			
			ps = new PrintStream(s.getOutputStream());
			
			while((content = readFromClient())!=null){
				if(content.startsWith(Constant.USER_NAEM_REGISTE)&&content.endsWith(Constant.USER_NAEM_REGISTE)){
					String userName = getRealMsg(content);
					if(MyServer.clients.containsKey(userName)){
						System.out.println("注册的用户名重复，请重新录入一个不同的用户名！");
						ps.println(Constant.NAME_REGISTER_FAIL);
					}else{
						System.out.println("注册成功");
						ps.println(Constant.LOGIN_SUCCESS);
						MyServer.clients.put(userName, ps);
					}
				}else if(content.startsWith(Constant.PRIVATE_TALK)&&content.endsWith(Constant.PRIVATE_TALK)){
					String userAndMsg = getRealMsg(content);
					String user = userAndMsg.split(Constant.SPIT_SIGN)[0];
					String time = userAndMsg.split(Constant.SPIT_SIGN)[1];
					String msg = userAndMsg.split(Constant.SPIT_SIGN)[2];
					MyServer.clients.get(user).println(user+Constant.SPIT_SIGN+MyServer.clients.getKeyByValue(ps)+Constant.SPIT_SIGN+time+Constant.SPIT_SIGN+msg+Constant.SPIT_SIGN+"*@");//将聊天信息发送给聊天者
					ps.println(user+Constant.SPIT_SIGN+MyServer.clients.getKeyByValue(ps)+Constant.SPIT_SIGN+time+Constant.SPIT_SIGN+msg+Constant.SPIT_SIGN+"&@");//将聊天信息发送给说话者
				}else{
					String userAndMsg = getRealMsg(content);
					String time = userAndMsg.split(Constant.SPIT_SIGN)[0];
					String msg = userAndMsg.split(Constant.SPIT_SIGN)[1];
					for(PrintStream cps:MyServer.clients.valueSet()){
						cps.println(MyServer.clients.getKeyByValue(ps)+":"+time+"\n"+msg+"\n");
					}
				}
				
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MyServer.clients.removeByValue(ps);
		} finally{
			try {
				s.close();	
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ps.close();
		}
		
	}
	
	private String readFromClient(){
		try {
			
			return br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private String getRealMsg(String line){
		return line.substring(Constant.LEN,line.length()-Constant.LEN);
	}
}
