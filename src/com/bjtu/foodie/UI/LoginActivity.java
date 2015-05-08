package com.bjtu.foodie.UI;

import com.bjtu.foodie.R;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.model.User;
import com.gltype.nourriture.ui.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
          	 Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show(); 
		}
		else{
			btn_login.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String username = et_username.getText().toString();
					String password = et_password.getText().toString();
					
					if(TextUtils.isEmpty(username.trim())||TextUtils.isEmpty(password.trim())){
						
					}
				}
			});
		}
	}
}
