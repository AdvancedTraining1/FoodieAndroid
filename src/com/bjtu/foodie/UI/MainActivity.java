package com.bjtu.foodie.UI;


import com.bjtu.foodie.R;
import android.view.View.OnClickListener;
import cn.jpush.android.api.JPushInterface;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import android.view.View.OnClickListener;
import android.widget.Button;
import cn.jpush.android.api.JPushInterface;

import com.bjtu.foodie.R;

public class MainActivity extends Activity {
	private Button btn_moment;
	private Button btn_date;
	private Button m_ToDish;
	private Button m_ToScanTag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		m_ToDish = (Button)findViewById(R.id.button_dish);
		m_ToScanTag = (Button)findViewById(R.id.btn_nfctag);
		
		JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        
        
        
        m_ToDish.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "ToDish",Toast.LENGTH_LONG ).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DishMenu.class);
                startActivityForResult(intent, 0);
            }
        });
        
        m_ToScanTag.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "To Scacn a Tag",Toast.LENGTH_LONG ).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, NFCScanTagActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        btn_moment = (Button) findViewById(R.id.btn_mmt);
        btn_moment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), MomentsActivity.class);
				startActivity(i);
			}
		});
        
        btn_date = (Button) findViewById(R.id.btn_dt);
        btn_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), DatesActivity.class);
				startActivity(i);
			}

		});
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
    public void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
	
	public void onPause()
	{
		super.onPause();
		JPushInterface.onPause(this);
	}
}
