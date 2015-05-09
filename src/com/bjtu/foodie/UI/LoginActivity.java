package com.bjtu.foodie.UI;

import org.json.JSONException;
import org.json.JSONObject;

import com.bjtu.foodie.R;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.model.User;
import com.bjtu.foodie.common.Constants;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private EditText et_username;
	private EditText et_password;
	private Button btn_back;
	private Button btn_register;
	private Button btn_login;
	public static String token;
	public boolean isConnect = false;
	public UserDao userDao = new UserDao(this);
	private Handler handler = new Handler();
	ConnectToServer connect = new ConnectToServer();
	ProgressDialog proDia ;
	String username;
	String password;
	String ifSuccess = "Failed";
	String url, message;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_login = (Button) findViewById(R.id.btn_login);
		
		User user = userDao.find();
		if(user!=null){
			isConnect = true;
			token = user.getToken();
//			userId = user.getUserId();
			System.out.println("-------------------"+token);
		}
		
		if(isConnect){
			LoginActivity.this.finish();
			Intent intent = new Intent(this, MainActivity.class);    
          	startActivity(intent);
          	//tv_result.setText(token);
          	 Toast.makeText(this, "You have logined", Toast.LENGTH_LONG).show(); 
		}
		else{
			btn_login.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					username = et_username.getText().toString();
					password = et_password.getText().toString();
					System.out.println("Account:" + username + "---------Password:"+ password);
					
					if(TextUtils.isEmpty(username.trim())||TextUtils.isEmpty(password.trim())){
						Toast.makeText(v.getContext(), "Account or Password can't be empty", Toast.LENGTH_LONG).show();
					}
					else{
						proDia = ProgressDialog.show(LoginActivity.this, "Login","Logining now,please wait!");
						proDia.show();
						new Thread()
						{
							public void run(){
								try{
									loginConnection(username, password);
									// 子线程不能修改main线程，所以必须用handler来对ui线程进行操作
									handler.post(new Runnable() {
										@Override
										public void run() {
											Toast.makeText(LoginActivity.this,ifSuccess, Toast.LENGTH_SHORT).show();
										}
									});
								}catch(Exception e){
									
								}finally{
									proDia.dismiss();
									Intent fromIntent = getIntent();
									if(fromIntent.getStringExtra(Constants.INTENT_EXTRA_MAIN_TAB) != null){
										LoginActivity.this.finish();
										Intent intent = new Intent();
										intent.setClass(LoginActivity.this,MainActivity.class);
										startActivity(intent);
									}else{
										LoginActivity.this.finish();
									}
								}
							}
						}.start();
					}
				}
			});
			
			btn_back.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					finish();
				}
			});
			
			btn_register.setOnClickListener(new OnClickListener(){
				public void onClick(View v) {
					Toast.makeText(LoginActivity.this, "Register",Toast.LENGTH_LONG ).show();
					LoginActivity.this.finish();
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, RegisterActivity.class);
		          	startActivity(intent);
				}
			});
		}
	}
	public void loginConnection(String username, String password) throws Exception{
		url = "/service/userinfo/login"; 
		StringBuffer params = new StringBuffer();
		// 表单参数与get形式一样
		params.append("username").append("=").append(username)
				.append("&").append("password").append("=")
				.append(password);
		byte[] bytes = params.toString().getBytes();  //变为字节
		message = connect.testURLConn2(url, bytes);
		JSONObject jsonObject = new JSONObject(message);
		
		try{
			token = jsonObject.getString("token");
			isConnect = true;
			userDao.add(token);
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		if(token != null)
			ifSuccess = "Successfully";
	}
}
