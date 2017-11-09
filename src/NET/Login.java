package NET;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JFrame{
	Socket socket = null;
	BufferedReader br = null;
	BufferedReader brServer = null;
	PrintStream ps = null;
	JButton btn =  new JButton("注册");
	JTextField tf = new JTextField();
	JLabel account = new JLabel("用户名:");
	JPanel p = new JPanel();
	JLabel tip = new JLabel();
	public Login(String name){
		super(name);
		setLayout(null);
		account.setBounds(10, 70, 50, 30);
		tf.setBounds(60, 70, 150, 30);
		tip.setBounds(60, 100, 200, 30);
		tip.setForeground(Color.RED);
		btn.setBounds(210, 70, 70, 30);
		btn.setFont(new Font("黑体",Font.BOLD, 9));
		add(account);
		add(tf);
		add(btn);
		add(tip);
		pack();
		setBounds(100, 100, 310, 250);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					socket = new Socket("127.0.0.1",30000);
					ps = new PrintStream(socket.getOutputStream());
					brServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					while(true){
						String userName = tf.getText();
						ps.println(Constant.USER_NAEM_REGISTE+userName+Constant.USER_NAEM_REGISTE);
						String result = brServer.readLine();
						if(Constant.NAME_REGISTER_FAIL.equals(result)){
							tip.setText("用户名重复，请重新输入用户名");
							continue;
						}
						if(Constant.LOGIN_SUCCESS.equals(result)){
							PersonObject.upFile(userName);
							new QQchat(userName,socket,brServer,ps).start();
							setVisible(false);
							break;
						}
					}
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		});
	}
	public static void main(String[] args) {
		new Login("注册窗口");
	}
}
