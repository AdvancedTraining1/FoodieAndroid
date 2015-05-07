package com.bjtu.foodie.UI;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.User;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
<<<<<<< HEAD
=======
import android.widget.ImageButton;
import android.widget.Toast;
>>>>>>> 0717863eb2ab66a14fb07015d36cc28fd7a22e53

public class RegisterActivity extends Activity {
	private EditText et_email;
	private EditText et_username;
	private EditText et_password;
	private EditText et_againPassword;
	private Button btn_register;
	private Button btn_back;
	
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
					User user = new User(username, email, password);
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
	
	
}
