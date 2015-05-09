package com.bjtu.foodie.UI;

import org.json.JSONObject;

import com.bjtu.foodie.R;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.model.User;
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
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private EditText et_email;
	private EditText et_username;
	private EditText et_password;
	private EditText et_againPassword;
	private Button btn_register;
	private Button btn_back;
	
	public static String token;
	//public boolean isConnect = false;
	public UserDao userDao = new UserDao(this);
	ConnectToServer connect;
	ProgressDialog proDia;
	Handler handler;
	String url, message;
	String result = "Register failed ";
	String username, password, againPassword, email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		et_againPassword = (EditText) findViewById(R.id.et_againPassword);
		et_email = (EditText) findViewById(R.id.et_email);
		btn_register =  (Button) findViewById(R.id.btn_register);
		btn_back = (Button) findViewById(R.id.btn_back);
		
		connect = new ConnectToServer();
		handler = new Handler();
		
		btn_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username = et_username.getText().toString();
				password = et_password.getText().toString();
				againPassword = et_againPassword.getText().toString();
				email = et_email.getText().toString();
				System.out.println("username：" + username+"-------password：" + password+"-------email:" + email);
				
				if(TextUtils.isEmpty(username.trim())){
					Toast.makeText(v.getContext(), "The username can't be empty", Toast.LENGTH_LONG).show();
				}
				else if(TextUtils.isEmpty(email.trim())){
					Toast.makeText(v.getContext(), "The email can't be empty", Toast.LENGTH_LONG).show();
				}
				else if(TextUtils.isEmpty(password.trim())){
					Toast.makeText(v.getContext(), "The password can't be empty", Toast.LENGTH_LONG).show();
				}
				else if(!password.equals(againPassword)){
					Toast.makeText(v.getContext(), "The enter the same password again", Toast.LENGTH_LONG).show();
				}
				else{
					proDia = ProgressDialog.show(RegisterActivity.this, "Register","Registering now,please wait!");
					proDia.show();
					new Thread() {
						@Override
						public void run() {
							try {
								User user = new User(username, email, password);
								registerConnection(user);
								handler.post(new Runnable() {
									@Override
									public void run() {
										Toast.makeText(RegisterActivity.this, result,Toast.LENGTH_SHORT).show();
									}
								});
							} catch (Exception e) {
							} finally {
								proDia.dismiss();
								Intent intent = new Intent();
								intent.setClass(RegisterActivity.this,LoginActivity.class);
								startActivity(intent);
							}
						}
					}.start();
				}
			}
		});
		
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	
	public void registerConnection(User user) throws Exception{
        url = "/service/userinfo/register"; 
        StringBuffer paramsBuffer = new StringBuffer();
        paramsBuffer.append("username").append("=").append(user.getUsername()).append("&")
        	.append("password").append("=").append(user.getPassword()).append("&")
        	.append("email").append("=").append(user.getEmail());
        byte[] bytes = paramsBuffer.toString().getBytes();
        result = connect.testURLConn2(url, bytes);
		
		loginAfterRegister(username, password);
	}
	
	public void loginAfterRegister(String username, String password) throws Exception{
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
			userDao.add(token);
			System.out.println("token:"+token);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
}
