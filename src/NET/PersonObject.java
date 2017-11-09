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
	public static void upFile(String name) throws Exception{//���л�
		Person p = new Person(name);
		File file=new File("person.txt");
		boolean isexist=false;//����һ�������ж��ļ��Ƿ���Ҫ�ص�ͷaced 0005��
        if(file.exists()){    //�ļ��Ƿ����
               isexist=true;
               FileOutputStream fo=new FileOutputStream(file,true);
               ObjectOutputStream oos = new ObjectOutputStream(fo);
               long pos=0;
              if(isexist){
                        pos=fo.getChannel().position()-4;//׷�ӵ�ʱ��ȥ��ͷ��aced 0005
                        fo.getChannel().truncate(pos);
                           }
                        oos.writeObject(p);//�������л�    
                        System.out.println("׷�ӳɹ�");
          }else{//�ļ�������
             file.createNewFile();
             FileOutputStream fo=new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fo);
             oos.writeObject(p);//�������л�
             oos.close();
             System.out.println("�״ζ������л��ɹ���");
          }
	}
	public static List<Person> downFile() throws FileNotFoundException, IOException, ClassNotFoundException {//�����л�
			
			File file = new File("person.txt");
				if(file.exists()){
				           ObjectInputStream ois;
				      try {
				           FileInputStream fn=new FileInputStream(file);
				           ois = new ObjectInputStream(fn);
				           while(fn.available()>0){//�����ļ���������
					           Person p = (Person)ois.readObject();//�����ж�ȡ����
						       personList.add(p);
				           }
				           ois.close();//ע����ѭ������ر�
				      	} catch (Exception e1) {
				        // TODO Auto-generated catch block
				      		e1.printStackTrace();
				        }
				}
				return personList;
	}
}
