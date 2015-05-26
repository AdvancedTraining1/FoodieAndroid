package com.bjtu.foodie.UI;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.RestaurantRecommendItemAdapter;
import com.bjtu.foodie.data.TestData;

import android.view.View.OnClickListener;
import cn.jpush.android.api.JPushInterface;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {
	private Button btn_moment;
	private Button btn_date;
	private Button m_ToDish;
	private Button btn_register;
	private Button btn_login;
	private Button btn_friend;

	private ListView lv_restRecommend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainpage);

		lv_restRecommend = (ListView) findViewById(R.id.lv_restRecommend);
		
		/**
		 * only use test data temporarily
		 */
		lv_restRecommend.setAdapter(new RestaurantRecommendItemAdapter(
				getApplicationContext(), TestData.getRecommendsData()));
		m_ToDish = (Button) findViewById(R.id.btn_dish);
		// btn_register = (Button)findViewById(R.id.btn_register);

		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);

		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);
			}

		});

		btn_register = (Button) findViewById(R.id.btn_register);
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
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

		/*
		 * btn_register.setOnClickListener(new OnClickListener() { public void
		 * onClick(View v) { Toast.makeText(MainActivity.this,
		 * "Register",Toast.LENGTH_LONG ).show(); Intent intent = new Intent();
		 * intent.setClass(MainActivity.this, RegisterActivity.class);
		 * startActivityForResult(intent, 0); } });
		 */
		btn_moment = (Button) findViewById(R.id.btn_moment);
		btn_moment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						MomentsActivity.class);
				startActivity(i);
			}
		});

		btn_date = (Button) findViewById(R.id.btn_date);
		btn_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						DatesActivity.class);
				startActivity(i);
			}

		});

		btn_friend = (Button) this.findViewById(R.id.btn_friend);
		btn_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						FriendListActivity.class);
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

	@Override
	public void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}
}
