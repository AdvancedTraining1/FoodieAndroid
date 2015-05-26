package com.bjtu.foodie.UI;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.bjtu.foodie.R;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.model.User;

public class MainActivity extends Activity {
	private Button btn_moment;
	private Button btn_date;
	private Button m_ToDish;
	private Button btn_register;
	private Button btn_login;
	private Button btn_friend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainpage);
		
		
		
		m_ToDish = (Button)findViewById(R.id.btn_dish);
		//btn_register = (Button)findViewById(R.id.btn_register);
		
		JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);
			}

		});
        
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
			}

		});
        
        
        m_ToDish.setOnClickListener(new OnClickListener() {
            @Override
			public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DishMenu.class);
                startActivityForResult(intent, 0);
            }
        });

        /*btn_register.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Register",Toast.LENGTH_LONG ).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 0);
            }
        });*/
        btn_moment = (Button) findViewById(R.id.btn_moment);
        btn_moment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), MomentsActivity.class);
				startActivity(i);
			}
		});
        
        btn_date = (Button) findViewById(R.id.btn_date);
        btn_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), DatesActivity.class);
				startActivity(i);
			}

		});
        
        btn_friend = (Button) this.findViewById(R.id.btn_friend);
        btn_friend.setOnClickListener(new OnClickListener() {
        	
			
			@Override
			public void onClick(View v) {
				
				System.out.println("sss---");
				UserDao userDao = new UserDao(MainActivity.this);
				User user = userDao.find();
				Handler handler = new Handler();
				if(user!=null){
					System.out.println("sss---"+user.getToken());
					
					
					String token = user.getToken();
					if(token.equals("")||token==null){						

						handler.post(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(MainActivity.this,"login frist", Toast.LENGTH_SHORT).show();
							}
						});
						
					}else{
						Intent i = new Intent(getApplicationContext(), FriendListActivity.class);
						startActivity(i);						
					}
				
				
			}else{
				
				handler.post(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MainActivity.this,"login frist", Toast.LENGTH_SHORT).show();
					}
				});
			}
				
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
	
	@Override
	public void onPause()
	{
		super.onPause();
		JPushInterface.onPause(this);
	}
}
