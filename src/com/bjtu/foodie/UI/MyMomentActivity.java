package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.SimpleMomentItemAdapter;
import com.bjtu.foodie.data.TestData;
import com.bjtu.foodie.model.Moment;

public class MyMomentActivity extends Activity {

	ListView lv_myMoments;
	List<Moment> myMomentList;
	SimpleMomentItemAdapter myMomentAdapter;
	ImageButton imgbtn_newMoment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_moments);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		lv_myMoments = (ListView) findViewById(R.id.lv_myMoments);
		imgbtn_newMoment = (ImageButton) findViewById(R.id.iv_photo);
		
		myMomentList = new ArrayList<Moment>();
		myMomentList = (new TestData()).getMomentsData();
		
		myMomentAdapter = new SimpleMomentItemAdapter(this, myMomentList);
		lv_myMoments.setAdapter(myMomentAdapter);
		
		imgbtn_newMoment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_moment, menu);
		return true;
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
}
