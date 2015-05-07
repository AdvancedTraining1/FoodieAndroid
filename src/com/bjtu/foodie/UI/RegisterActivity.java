package com.bjtu.foodie.UI;

import com.bjtu.foodie.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class RegisterActivity extends Activity {
	private EditText et_email;
	private EditText et_username;
	private EditText et_password;
	private EditText et_againPassword;
	private Button btn_register;
	private Button btn_back;
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
	}
	
	
}
