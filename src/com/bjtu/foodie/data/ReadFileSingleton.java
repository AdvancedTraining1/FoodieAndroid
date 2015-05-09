package com.bjtu.foodie.data;

import java.io.ByteArrayOutputStream;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
  
import android.app.Activity;  
import android.content.Context;
import android.os.Bundle;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.widget.Button;  
import android.widget.EditText;  
import android.widget.TextView;  
import android.widget.Toast; 

//懒汉式单例类.在第一次调用的时候实例化自己   
public class ReadFileSingleton extends Activity{  
  //私有的默认构造子  
  private ReadFileSingleton() {String filename = "NFC.dat"; }  
  //注意，这里没有final      
  private static ReadFileSingleton single=null;  
  private static String filename;
  //静态工厂方法   
  public static ReadFileSingleton getInstance() {  
       if (single == null) {    
           single = new ReadFileSingleton();  
       }    
      return single;  
  } 
  public void WriteFile(Activity context,String text) {
	  
	  //Toast.makeText(context, "To another page",Toast.LENGTH_SHORT).show();
	  try {  
          /* 根据用户提供的文件名，以及文件的应用模式，打开一个输出流.文件不存系统会为你创建一个的， 
           * 至于为什么这个地方还有FileNotFoundException抛出，我也比较纳闷。在Context中是这样定义的 
           *   public abstract FileOutputStream openFileOutput(String name, int mode) 
           *   throws FileNotFoundException; 
           * openFileOutput(String name, int mode); 
           * 第一个参数，代表文件名称，注意这里的文件名称不能包括任何的/或者/这种分隔符，只能是文件名 
           *          该文件会被保存在/data/data/应用名称/files/chenzheng_java.txt 
           * 第二个参数，代表文件的操作模式 
           *          MODE_PRIVATE 私有（只能创建它的应用访问） 重复写入时会文件覆盖 
           *          MODE_APPEND  私有   重复写入时会在文件的末尾进行追加，而不是覆盖掉原来的文件 
           *          MODE_WORLD_READABLE 公用  可读 
           *          MODE_WORLD_WRITEABLE 公用 可读写 
           *  */  
          FileOutputStream outputStream = openFileOutput(filename,  
                  Activity.MODE_APPEND);  
          Toast.makeText(context, "To another page",Toast.LENGTH_SHORT).show();
          outputStream.write(text.getBytes());  
          outputStream.flush();  
          outputStream.close();  
          //Toast.makeText(FileTest.this, "保存成功", Toast.LENGTH_LONG).show();  
      } catch (FileNotFoundException e) {  
          e.printStackTrace();  
      } catch (IOException e) {  
          e.printStackTrace();  
      }  
  }
  public String ReadFromFile(Context context) {
	  String content = null;
	  try {  
		  
          FileInputStream inputStream = context.openFileInput(filename);  
          byte[] bytes = new byte[1024];  
          ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();  
          while (inputStream.read(bytes) != -1) {  
              arrayOutputStream.write(bytes, 0, bytes.length);  
          }  
          inputStream.close();  
          arrayOutputStream.close();  
          content = new String(arrayOutputStream.toByteArray());  
          

      } catch (FileNotFoundException e) {  
          e.printStackTrace();  
      } catch (IOException e) {  
          e.printStackTrace();  
      }  
	  return content;

  }
	

}  