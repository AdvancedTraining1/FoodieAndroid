package com.bjtu.foodie.UI;

import com.bjtu.foodie.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	
	private EditText et_username;
	private EditText et_password;
	private Button btn_back;
	private Button btn_register;
	private Button btn_login;
	public static String token;
	public boolean isConnect = false;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_login = (Button) findViewById(R.id.btn_login);
	}
}
