package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.bjtu.foodie.R;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.utils.DatesTalkToServer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddDateActivity extends Activity {
	
	private Button btn_select;
	private EditText et_friends;
	private EditText et_time;
	private EditText et_content;
	private Button btn_send;
	
	//public static String token;
	
	ConnectToServer connect;
	ProgressDialog proDia;
	Handler handler;
	String url, message;
	String result = "Publish date failed ";
	String friends="", time, content;
	ArrayList<String> friendIds,friendNames;
	String ids="";
	  
    private Button pickDate = null;  
  
    private static final int DATE_DIALOG_ID = 1;  
  
    private static final int SHOW_DATAPICK = 0;  
  
    private int mYear;  
  
    private int mMonth;  
  
    private int mDay;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_date);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		et_friends = (EditText) findViewById(R.id.friends);
		et_time = (EditText) findViewById(R.id.time);
		et_content = (EditText) findViewById(R.id.content);		
		btn_select=(Button)findViewById(R.id.select);
		btn_send=(Button)findViewById(R.id.publish);
		
		connect = new ConnectToServer();
		handler = new Handler();
		
		//============transmit friend list back to add page
		
		Intent intent = getIntent();
		String flag=intent.getStringExtra(Constants.flag);
		System.out.println("flag==="+flag);
		if(flag!=null&&flag.equals("have selected friends")){
			
			friendIds = getIntent().getStringArrayListExtra("friendIdList");
			friendNames = getIntent().getStringArrayListExtra("friendNameList");
			//friends = intent.getStringExtra(Constants.KEY_PHOTO_PATH);			
			
			for(int i=0;i<friendNames.size();i++){
				if(friendNames.get(i)!=null){
					friends= friends+friendNames.get(i)+".";
					ids= ids+friendIds.get(i)+",";
				}
				
			} 
			System.out.println("friends====="+friends);
			System.out.println("ids====="+ids);
			et_friends.setText(friends);
			
		}
		
		//============================================================
		
		
		   pickDate = (Button) findViewById(R.id.but_showDate);  
		  
	       pickDate.setOnClickListener(new DateButtonOnClickListener());  
	  
	       final Calendar c = Calendar.getInstance();  
	  
	       mYear = c.get(Calendar.YEAR);  
	  
	       mMonth = c.get(Calendar.MONTH);  
	  
	       mDay = c.get(Calendar.DAY_OF_MONTH);  
	  
	       setDateTime();
		
		
		btn_select.setOnClickListener(new OnClickListener() {
            @Override
			public void onClick(View v) {
                Toast.makeText(AddDateActivity.this, "select friends",Toast.LENGTH_LONG ).show();
                Intent intent = new Intent();
                intent.setClass(AddDateActivity.this, SelectDateFriendsActivity.class);
                startActivityForResult(intent, 0);
				
            }
        });
		
		
		btn_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//friends = et_friends.getText().toString();
				
				time = et_time.getText().toString();
				content = et_content.getText().toString();
				System.out.println("friends：" + friends+"-------time：" + time+"-------content:" + content);
				
				if(TextUtils.isEmpty(friends.trim())){
					Toast.makeText(v.getContext(), "The friends can't be empty", Toast.LENGTH_LONG).show();
				}
				else if(TextUtils.isEmpty(time.trim())){
					Toast.makeText(v.getContext(), "The time can't be empty", Toast.LENGTH_LONG).show();
				}
				else if(TextUtils.isEmpty(content.trim())){
					Toast.makeText(v.getContext(), "The content can't be empty", Toast.LENGTH_LONG).show();
				}
				else{
					//proDia = ProgressDialog.show(AddDateActivity.this, "Publish Date","Publishing date now,please wait!");
					//proDia.show();
					
					createDate();
					
				}
			}
		});
		
	}
	
	public void createDate(){
		if(LoginActivity.token != null){
			
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair(Constants.POST_TOKEN, LoginActivity.token));
	        postParameters.add(new BasicNameValuePair("time", time));
	        postParameters.add(new BasicNameValuePair("content", content));
	        postParameters.add(new BasicNameValuePair("friendids", ids));
	        postParameters.add(new BasicNameValuePair("friendnames", friends));
	        
	        String resultString = DatesTalkToServer.datesPost("date/create",postParameters);
	        
	        if(resultString.equals("publish date success")){
	        	//proDia.dismiss();
	        	Toast.makeText(getApplicationContext(), "publish success!",
					     Toast.LENGTH_SHORT).show();
	        	
	        	finish();
	    		Intent intent = new Intent(AddDateActivity.this, AddDateActivity.class);
	    		startActivity(intent);
	        }else{
	        	//proDia.dismiss();
	        	Toast.makeText(getApplicationContext(), "publish fail!",
					     Toast.LENGTH_SHORT).show();
	        }
		}else{
			Intent loginIntent = new Intent(this, LoginActivity.class);
			startActivity(loginIntent);
		}
	}
	
//==========================================日期控件=====================	
	private void setDateTime() {  
		  
	       final Calendar c = Calendar.getInstance();  
	  
	       mYear = c.get(Calendar.YEAR);  
	  
	       mMonth = c.get(Calendar.MONTH);  
	  
	       mDay = c.get(Calendar.DAY_OF_MONTH);  
	  
	   
	  
	       updateDisplay();  
	  
	}  
	
	/** 
	 
     * 更新日期 
 
     */  
  
    private void updateDisplay() {  
  
    	et_time.setText(new StringBuilder().append(mYear).append(  
  
              (mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append(  
  
              (mDay < 10) ? "0" + mDay : mDay));  
  
    }  
	
    
    /** 
    
     * 日期控件的事件 
 
     */  
  
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {  
  
       @Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,  
  
              int dayOfMonth) {  
  
           mYear = year;  
  
           mMonth = monthOfYear;  
  
           mDay = dayOfMonth;  
  
           updateDisplay();  
  
       }  
  
    }; 
    
    
    /** 
    
     * 选择日期Button的事件处理 
 
     */  
  
    class DateButtonOnClickListener implements  
  
           android.view.View.OnClickListener {  
  
       @Override  
  
       public void onClick(View v) {  
  
           Message msg = new Message();  
  
           if (pickDate.equals(v)) {  
  
              msg.what = AddDateActivity.SHOW_DATAPICK;  
  
           }  
  
           AddDateActivity.this.saleHandler.sendMessage(msg);  
  
       }  
  
    }  
	
    @Override  
    
    protected Dialog onCreateDialog(int id) {  
  
       switch (id) {  
  
       case DATE_DIALOG_ID:  
  
           return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,  
  
                  mDay);  
  
       }  
  
       return null;  
  
    }  
    
    @Override  
    
    protected void onPrepareDialog(int id, Dialog dialog) {  
  
       switch (id) {  
  
       case DATE_DIALOG_ID:  
  
           ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);  
  
           break;  
  
       }  
  
    }  
    
    /** 
    
     * 处理日期控件的Handler 
 
     */  
  
    Handler saleHandler = new Handler() {  
  
       @Override  
  
       public void handleMessage(Message msg) {  
  
           switch (msg.what) {  
  
           case AddDateActivity.SHOW_DATAPICK:  
  
              showDialog(DATE_DIALOG_ID);  
  
              break;  
  
           }  
  
       }  
  
    }; 
    
    
    
//==========================================日期控件=====================	
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_moment, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();
		switch (item_id) {
		case R.id.new_action_send:
			Toast.makeText(this, "Sending~~", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
