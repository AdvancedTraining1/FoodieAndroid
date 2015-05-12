package com.bjtu.foodie.UI;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bjtu.foodie.R;

public class MomentAddActivity1 extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moment_add_1);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public void takePhoto(View view){
		Toast.makeText(getApplicationContext(), "takePhoto",
			     Toast.LENGTH_SHORT).show();
	}
	
	public void choosePhoto(View view){
		Toast.makeText(getApplicationContext(), "choosePhoto",
			     Toast.LENGTH_SHORT).show();
	}
}
