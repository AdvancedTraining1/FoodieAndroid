package com.bjtu.foodie.adapter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.bjtu.foodie.R;
import com.bjtu.foodie.UI.NFCScanTagActivity;
import com.bjtu.foodie.model.DishItem;

import android.R.array;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FullDishItemsAdapter extends BaseAdapter {

	private ArrayList<String> data;
	private Context context;
	private FullDishItemsAdapter adapter;

	public FullDishItemsAdapter(Context contex, ArrayList<String> dishes) {
		this.data = dishes;
		this.context = contex;
	}

	@Override
	public int getCount() {
		//Toast.makeText(context, "number" + dishes.size(), Toast.LENGTH_SHORT).show();
		return data == null ? 0 : data.size()-1;
	}

	@Override
	public Object getItem(int arg0) {
		return data == null ? null : data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public String getId(int arg0) throws JSONException {
		JSONTokener jsonParser = new JSONTokener(data.get(arg0));  
	    // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。  
	    // 如果此时的读取位置在"name" : 了，那么nextValue就是"yuanzhifei89"（String）  
	    JSONObject person = (JSONObject) jsonParser.nextValue();  
	    // 接下来的就是JSON对象的操作了  
	   
	    //person.getString("name");  
	   // person.getInt("age"); 
	   // Toast.makeText(FullDateItemAdapter.this, person.getString("id") ,Toast.LENGTH_SHORT).show();
		return person.getString("id");
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		View view = arg1;
		Holder holder;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.dishlist,null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}

		//String dish = this.data.get(position);
		JSONTokener jsonParser = new JSONTokener(this.data.get(position));  
		
		
		
		try {
			JSONObject person = (JSONObject) jsonParser.nextValue();  
			holder.title.setText(person.getString("id"));
			holder.text.setText(person.getString("text"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//view.setBackgroundColor(dish.getColor());
		
		//---------getPic == id of the pic in drawable-------
		holder.iamge.setImageResource(R.drawable.jay);
		holder.button.setOnClickListener(new OnClickCommitBtnListener(position));
		holder.button1.setOnClickListener(new OnClickCommitBtnListener1(position));
		return view;
	}
	
	private class OnClickCommitBtnListener implements OnClickListener {
		private int pos;
		
		public OnClickCommitBtnListener(int pos) {
			this.pos = pos;
		}
		@Override
		public void onClick(View v) {
			Toast.makeText(context, "On click delete item" + pos, Toast.LENGTH_SHORT).show();
			data.remove(pos);
			String temp = "";
			for (int i = 0; i < data.size()-1; i++) {
				temp = temp+data.get(i)+"\n";
			}
			//String temp = "123";
			ArrayList<String> temArrayList = ReadFromFile();
			Toast.makeText(context, temArrayList.get(0),Toast.LENGTH_SHORT).show();
			// context.setListAdapter(this); 
			
			 
			WriteFile(temp);
			adapter.notifyDataSetChanged();
			
			//adapter.notifyAll();
		}
	}
	private class OnClickCommitBtnListener1 implements OnClickListener {
		private int pos;
		
		public OnClickCommitBtnListener1(int pos) {
			this.pos = pos;
		}
		@Override
		public void onClick(View v) {
			Toast.makeText(context, "On click delete item" + pos, Toast.LENGTH_SHORT).show();
			data.remove(pos);
			String temp = "";
			for (int i = 0; i < data.size()-1; i++) {
				temp = temp+data.get(i)+"\n";
			}
			//String temp = "123";
			ArrayList<String> temArrayList = ReadFromFile();
			Toast.makeText(context, temArrayList.get(0),Toast.LENGTH_SHORT).show();
			// context.setListAdapter(this); 
			
			 
			WriteFile(temp);
			adapter.notifyDataSetChanged();
			
			//adapter.notifyAll();
		}
	}
	public void WriteFile(String text) {
	  	  
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
	            FileOutputStream outputStream = context.openFileOutput("NFC.dat",  
	                    Activity.MODE_PRIVATE);  
	            //Toast.makeText(context, "To another page",Toast.LENGTH_SHORT).show();           
	            
	            outputStream.write(text.getBytes());  
	            //outputStream.write(new String("\n").getBytes());  
	            outputStream.flush();  
	            outputStream.close();  
	            //Toast.makeText(FileTest.this, "保存成功", Toast.LENGTH_LONG).show();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }
	
	public ArrayList<String> ReadFromFile() {
    	ArrayList<String> m_Data = null;
      
      int count = 0;
  	  String content = null;
  	  try {  
  		  
            FileInputStream inputStream = context.openFileInput("NFC.dat");  
            byte[] bytes = new byte[1024];  
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();  
            while (inputStream.read(bytes) != -1) {  
                arrayOutputStream.write(bytes, 0, bytes.length);  
            }  
            inputStream.close();  
            arrayOutputStream.close();  
            content = new String(arrayOutputStream.toByteArray());  
            //String result=content.substring(0,strlastIndexOf("."));
            
            String split = "\n";
            StringTokenizer token = new StringTokenizer(content, split);
            m_Data = new ArrayList<String >();
            while (token.hasMoreTokens()) {
            	m_Data.add(count,token.nextToken());
            	count++;
            	//Toast.makeText(NFCScanTagActivity.this, token.nextToken() ,Toast.LENGTH_SHORT).show();
            }

            
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  	  return m_Data;

    }

	private static class Holder {
		ImageView iamge = null;
	    TextView title = null;
	    TextView text = null;
	    Button button = null;
	    Button button1 = null;

		public Holder(View view) {
			iamge = (ImageView) view.findViewById(R.id.array_image);
    		title = (TextView) view.findViewById(R.id.array_title);
    		text = (TextView) view.findViewById(R.id.array_text);
    		button = (Button)view.findViewById(R.id.array_button);
    		button1 = (Button)view.findViewById(R.id.array_button0);
		}
	}
	
	public void setAdapter(FullDishItemsAdapter a) {
		adapter = a;
	}
}

