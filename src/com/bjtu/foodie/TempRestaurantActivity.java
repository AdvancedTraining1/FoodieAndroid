package com.bjtu.foodie;

import com.baidu.mapapi.model.LatLng;
import com.bjtu.foodie.model.Restaurant;
import com.bjtu.foodie.model.RestaurantForSerializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class TempRestaurantActivity extends Activity {

	private Restaurant restaurant;
	TextView tv_uid,tv_name, tv_addr, tv_type;
	TextView tv_envRate, tv_FctRate, tv_hygRate, tv_servRate, tv_tstRate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temp_restaurant);

		tv_uid = (TextView) findViewById(R.id.tv_uid);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_addr = (TextView) findViewById(R.id.tv_address);
		tv_type = (TextView) findViewById(R.id.tv_type);
		tv_envRate = (TextView) findViewById(R.id.tv_envRate);
		tv_FctRate = (TextView) findViewById(R.id.tv_FctRate);
		tv_hygRate = (TextView) findViewById(R.id.tv_hygRate);
		tv_servRate = (TextView) findViewById(R.id.tv_ServRate);
		tv_tstRate = (TextView) findViewById(R.id.tv_tstRate);

		Intent intentFromMap = getIntent();
		restaurant = new Restaurant(
				(RestaurantForSerializable) intentFromMap
						.getSerializableExtra("restauInfo"));
		restaurant.setLocation(new LatLng(intentFromMap.getExtras().getDouble(
				"latitude"), intentFromMap.getExtras().getDouble("longitude")));
		
		tv_uid.setText(restaurant.getUid());		
		tv_name.setText(restaurant.getName());		
		tv_addr.setText(restaurant.getAddress());		
		tv_type.setText(restaurant.getType());	
		
		// need to change into stars
		tv_envRate.setText(""+restaurant.getEnvironmentRate());		
		tv_FctRate.setText(""+restaurant.getFacilityRate());		
		tv_hygRate.setText(""+restaurant.getFacilityRate());		
		tv_servRate.setText(""+restaurant.getServiceRate());		
		tv_tstRate.setText(""+restaurant.getTasteRate());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.temp_restaurant, menu);
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
