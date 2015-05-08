package com.bjtu.foodie.UI;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private EditText et_email;
	private EditText et_username;
	private EditText et_password;
	private EditText et_againPassword;
	private Button btn_register;
	private Button btn_back;
	
	ConnectToServer connect;
	ProgressDialog proDia;
	Handler handler;
	String url;
	String result;
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
										Toast.makeText(RegisterActivity.this, "Register successful!",Toast.LENGTH_SHORT).show();
									}
								});
							} catch (Exception e) {
							} finally {
								proDia.dismiss();
								Intent intent = new Intent();
								intent.setClass(RegisterActivity.this,MainActivity.class);
								startActivity(intent);
							}

						}
					}.start();
				}
			}
		});
		
		btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
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
		JSONObject jsonObj = new JSONObject(result);
		String message = jsonObj.getString("message");
		System.out.println("RegisterMessage---" + message);
	}
	
	public void loginAfterRegister(){
		
	}
	
}
