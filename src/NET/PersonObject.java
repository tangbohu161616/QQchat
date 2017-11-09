package NET;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class PersonObject {
	public static List<Person> personList = new ArrayList<>();
	public static void upFile(String name) throws Exception{//序列化
		Person p = new Person(name);
		File file=new File("person.txt");
		boolean isexist=false;//定义一个用来判断文件是否需要截掉头aced 0005的
        if(file.exists()){    //文件是否存在
               isexist=true;
               FileOutputStream fo=new FileOutputStream(file,true);
               ObjectOutputStream oos = new ObjectOutputStream(fo);
               long pos=0;
              if(isexist){
                        pos=fo.getChannel().position()-4;//追加的时候去掉头部aced 0005
                        fo.getChannel().truncate(pos);
                           }
                        oos.writeObject(p);//进行序列化    
                        System.out.println("追加成功");
          }else{//文件不存在
             file.createNewFile();
             FileOutputStream fo=new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fo);
             oos.writeObject(p);//进行序列化
             oos.close();
             System.out.println("首次对象序列化成功！");
          }
	}
	public static List<Person> downFile() throws FileNotFoundException, IOException, ClassNotFoundException {//反序列化
			
			File file = new File("person.txt");
				if(file.exists()){
				           ObjectInputStream ois;
				      try {
				           FileInputStream fn=new FileInputStream(file);
				           ois = new ObjectInputStream(fn);
				           while(fn.available()>0){//代表文件还有内容
					           Person p = (Person)ois.readObject();//从流中读取对象
						       personList.add(p);
				           }
				           ois.close();//注意在循环外面关闭
				      	} catch (Exception e1) {
				        // TODO Auto-generated catch block
				      		e1.printStackTrace();
				        }
				}
				return personList;
	}
}
