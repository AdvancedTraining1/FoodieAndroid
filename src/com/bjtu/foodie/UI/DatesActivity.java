package com.bjtu.foodie.UI;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.FullDateItemAdapter;
import com.bjtu.foodie.data.TestData;
import com.bjtu.foodie.map.MapActivity;
import com.bjtu.foodie.model.DateModel;

public class DatesActivity extends Activity {

	// Listview adapter List<dates> List<comment>
	private ListView lv_allDates;
	private List<DateModel> list_dates;
	private FullDateItemAdapter dateAdapter;

	private ShareActionProvider myProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dates);
		
		lv_allDates = (ListView) findViewById(R.id.lv_dates);
		
		list_dates = (new TestData()).getDatesData();
		dateAdapter = new FullDateItemAdapter(this, list_dates);

		lv_allDates.setAdapter(dateAdapter);
/*
		tv_username.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentMyMoment = new Intent(getApplicationContext(),
						MyMomentActivity.class);
				
				startActivity(intentMyMoment);
			}
		});*/
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.moments, menu);
		
		MenuItem shareItem = menu.findItem(R.id.action_share);
		myProvider = (ShareActionProvider) shareItem.getActionProvider();
		myProvider.setShareIntent(getDefaultIntent());
		
		return true;
	}

	private Intent getDefaultIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		return shareIntent;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_addMoments:
			Intent intentNewDate = new Intent(this, AddDateActivity.class);
			startActivity(intentNewDate);
			break;
		case R.id.action_map:
			Intent intentMap = new Intent(this, MapActivity.class);
			startActivity(intentMap);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
