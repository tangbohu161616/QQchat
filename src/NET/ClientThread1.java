package NET;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ClientThread1 implements Runnable{
	BufferedReader br =null;
	JTextArea ta = null;
	JTextArea tt = null;
	JTextArea t = null;
	List<Person> personList = null;
	public ClientThread1(BufferedReader br,JTextArea ta,JTextArea tt,JTextArea t,List<Person> personList){
			this.br=br;
			this.ta = ta;
			this.tt=tt;
			this.t=t;
			this.personList=personList;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String content = null;
		try {
			Thread.currentThread().sleep(500);
			while((content=br.readLine())!=null){
				if(content.endsWith("@")){
					String user = content.split(Constant.SPIT_SIGN)[0];
					String self = content.split(Constant.SPIT_SIGN)[1];
					String time = content.split(Constant.SPIT_SIGN)[2];
					String msg = content.split(Constant.SPIT_SIGN)[3];
					if(user.equals(personList.get(1).getName())&&self.equals(personList.get(0).getName())){
						t.append(self+":"+time+"\n"+msg+"\n");
					}else if(user.equals(personList.get(0).getName())&&self.equals(personList.get(1).getName())){
						t.append(self+":"+time+"\n"+msg+"\n");
					}else if(user.equals(personList.get(2).getName())&&self.equals(personList.get(0).getName())){
						if(content.charAt(content.length()-2)=='&'){
							tt.append(self+":"+time+"\n"+msg+"\n");
						}
						if(content.charAt(content.length()-2)=='*'){
							t.append(self+":"+time+"\n"+msg+"\n");
						}
					}else if(user.equals(personList.get(0).getName())&&self.equals(personList.get(2).getName())){
						if(content.charAt(content.length()-2)=='&'){
							t.append(self+":"+time+"\n"+msg+"\n");
						}
						if(content.charAt(content.length()-2)=='*'){
							tt.append(self+":"+time+"\n"+msg+"\n");
						}
					}else if(user.equals(personList.get(2).getName())&&self.equals(personList.get(1).getName())){
						tt.append(self+":"+time+"\n"+msg+"\n");
					}else if(user.equals(personList.get(1).getName())&&self.equals(personList.get(2).getName())){
						tt.append(self+":"+time+"\n"+msg+"\n");
					}
				}else if(!content.endsWith("@")){
					ta.append(content+"\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
