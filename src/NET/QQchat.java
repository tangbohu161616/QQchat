package NET;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.*;

public class QQchat extends Thread{

	/**
	 * @param args
	 */
	JFrame f = new JFrame("QQ聊天界面");
	JPanel topane = new JPanel();
	JPanel dopane = new JPanel();
	JPanel inpane = new JPanel();
	JPanel btpane = new JPanel();
	ImageIcon icon = new ImageIcon("image/timg.jpg");
	JLabel graph = new JLabel(icon);
	JLabel qqname;
	JLabel menu = new JLabel(new ImageIcon("image/all.png"));
	JLabel menu1 = new JLabel(new ImageIcon("image/bottom.png"));
	JButton image = new JButton("关闭");
	JButton cut = new JButton("发送");
	JTextArea ta = new JTextArea(15,8);
	JTextArea t = new JTextArea(15,8);//三个对话框
	JTextArea tt = new JTextArea(15,8);
	JTextArea ta1 = new JTextArea(5,8);
	JScrollPane tajsp = null;
	JScrollPane tajsp1 = null;
	JScrollPane tajsp2 = null;
	CardLayout card =new CardLayout();
	JLabel qqshow = new JLabel(new ImageIcon("image/vniem.png"));
	JMenuItem copyItem = new JMenuItem("复制");
	JPopupMenu pop = new JPopupMenu();
	JMenuItem copyItem1 = new JMenuItem("复制");
	JMenuItem paseItem1 = new JMenuItem("粘贴");
	JPopupMenu pop1 = new JPopupMenu();
	String getFont = null;
	final int column=38;
	Socket socket=null;
	BufferedReader br=null;
	PrintStream ps = null;
	List<Person> personList = null;//得到聊天室人物信息
	JButton graph1[] = null;
	JPanel Image[] = null;
	JPanel Lepane = new JPanel();
	String name;
	public QQchat(String name,Socket socket,BufferedReader br,PrintStream ps){
		this.socket=socket;
		this.br=br;
		final PrintStream Ps=ps;
		qqname = new JLabel(name);
		pop.add(copyItem);
		ta.setComponentPopupMenu(pop);
		ta.setEditable(false);
		pop1.add(copyItem1);
		pop1.add(paseItem1);
		ta1.setComponentPopupMenu(pop1);
		KeyStroke enter = KeyStroke.getKeyStroke("ENTER");//屏蔽文本框的enter键
		ta1.getInputMap().put(enter, "none");
		topane.add(menu);
		inpane.add(menu1);
		image.setBackground(Color.lightGray);
		cut.setBackground(Color.lightGray);
		btpane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btpane.setBackground(Color.WHITE);
		btpane.add(image);
		btpane.add(cut);
		ActionListener a = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Calendar c= Calendar.getInstance();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss:SSS");
				String line = null;
				int i =0;
				if(ta1.getText()!=null&&!ta1.getText().equals("")){
					String s = format.format(c.getTime());
					/*ta.append(s+"\n");
					ta.append(ta1.getText()+"\n");
					ta1.setText("");*/
					/*sb.append(s+"\n");
					sb.append(ta1.getText()+"\n");*/
					line = s+Constant.SPIT_SIGN+ta1.getText();
					for (; i < 3; i++) {
						if(Image[i].getBackground()==Color.cyan && !graph1[i].getText().equals("群聊")){
							line = Constant.PRIVATE_TALK+graph1[i].getText()+Constant.SPIT_SIGN+line+Constant.PRIVATE_TALK;
							System.out.println("1"+" "+line);
							Ps.println(line);
						}else if(Image[i].getBackground()==Color.cyan && graph1[i].getText().equals("群聊")){
							System.out.println(2);
							Ps.println(Constant.PUBLIC_TALK+line+Constant.PUBLIC_TALK);
						}	
					}
					ta1.setText("");
				}
			}
		};
		cut.addActionListener(a);
		cut.registerKeyboardAction(a,KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);//给发送按钮添加enter快捷键
		image.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		copyItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getFont=ta.getSelectedText();
			}
		});
		copyItem1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getFont=ta1.getSelectedText();
			}
		});
		paseItem1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ta1.append(getFont);
			}
		});
		ta1.addKeyListener(new KeyAdapter() {
		      public void keyPressed(final KeyEvent e) {
			        String text = ta1.getText().replace("\n", "");
			        int line = text.length() / column;
			        StringBuffer buffer = new StringBuffer();
			        int i;
			        for (i = 0; i < line; i++) {
			          buffer.append(text.substring(i * column, (i + 1) * column));
			          buffer.append("\r\n");
			        }
			        buffer.append(text.substring(i * column));
			        ta1.setText(buffer.toString());
			      }
		});
		f.setLayout(null);
		tajsp = new JScrollPane(ta);
		tajsp1 = new JScrollPane(t);
		tajsp2 = new JScrollPane(tt);
		dopane.setLayout(card);
		dopane.add("tajsp",tajsp);
		dopane.add("tajsp1",tajsp1);
		dopane.add("tajsp2",tajsp2);
		JPanel top = new JPanel();
		top.setLayout(new FlowLayout(FlowLayout.LEFT));
		top.add(graph);
		top.add(qqname);
		Box bigtop = Box.createVerticalBox();
		bigtop.add(top);
		bigtop.add(topane);
		bigtop.add(dopane);
		bigtop.add(inpane);
		bigtop.add(ta1);
		bigtop.add(btpane);
		bigtop.setBounds(85, 0, 460, 520);
		qqshow.setBounds(545, 0, 180, 520);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(bigtop);
		f.add(qqshow);
		f.setBounds(100, 100,725,525);
		f.pack();
		f.setVisible(true);
	}
	public void run(){
		try {
			sleep(2000);
			personList = PersonObject.downFile();
			Image =new JPanel[personList.size()];
			graph1 = new JButton[personList.size()];
			f.setLayout(null);
			Lepane.setLayout(null);
			Lepane.setBounds(0, 0, 85, 525);
			Lepane.setBackground(Color.LIGHT_GRAY);
			int y = 5;
			int j =0;
			name = qqname.getText();
			for (int i=0; i < personList.size(); i++) {//私聊头像
				if(!qqname.getText().equals(personList.get(i).getName())){//如果文本中的名字不是自己就加入聊天对话序列	
					Image[j]= new JPanel();
					Image[j].setBounds(0, y, 85, 45);
					Image[j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
					Image[j].setBackground(Color.white);
					Image[j].setLayout(new FlowLayout(FlowLayout.LEFT));
					ImageIcon icon = new ImageIcon("image/timg.jpg");
					graph1[j] = new JButton(personList.get(i).getName(),icon);
					graph1[j].setBackground(Color.white);
					graph1[j].setFocusable(false);
					Image[j].add(graph1[j]);
					Lepane.add(Image[j]);
					y+=45;
					j++;
				}
			}
			for (int i = 0; i < personList.size(); i++) {//群聊头像
				if(Image[i]==null){
					Image[i]= new JPanel();
					Image[i].setBounds(0, y, 85, 45);
					Image[i].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
					Image[i].setBackground(Color.white);
					Image[i].setLayout(new FlowLayout(FlowLayout.LEFT));
					ImageIcon icon = new ImageIcon("image/timg.jpg");
					graph1[i] = new JButton("群聊",icon);
					graph1[i].setBackground(Color.white);
					graph1[i].setFocusable(false);
					Image[i].add(graph1[i]);
					Lepane.add(Image[i]);
					y+=45;
				}
			}
			graph1[0].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					qqname.setText(name+"---"+graph1[0].getText()+" 聊天中");
					card.show(dopane, "tajsp1");
					System.out.println("t");
					Image[0].setBackground(Color.cyan);
					Image[1].setBackground(Color.white);
					Image[2].setBackground(Color.white);
				}
			});
			graph1[1].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					qqname.setText(name+"---"+graph1[1].getText()+" 聊天中");
					card.show(dopane, "tajsp2");
					System.out.println("tt");
					Image[1].setBackground(Color.cyan);
					Image[2].setBackground(Color.white);
					Image[0].setBackground(Color.white);
				}
			});
			graph1[2].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					qqname.setText(name+"---"+graph1[2].getText()+" 聊天中");
					card.show(dopane, "tajsp");
					System.out.println("ta");
					Image[2].setBackground(Color.cyan);
					Image[0].setBackground(Color.white);
					Image[1].setBackground(Color.white);
				}
			});
			f.add(Lepane);
			new Thread(new ClientThread1(br,ta,tt,t,personList)).start();//得到服务器发送的话
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
		}
	}
}
