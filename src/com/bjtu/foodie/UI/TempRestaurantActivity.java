package com.bjtu.foodie.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.bjtu.foodie.R;
import com.bjtu.foodie.R.drawable;
import com.bjtu.foodie.R.id;
import com.bjtu.foodie.R.layout;
import com.bjtu.foodie.R.menu;
import com.bjtu.foodie.map.MapUtils;
import com.bjtu.foodie.model.Restaurant;
import com.bjtu.foodie.model.RestaurantForSerializable;

public class TempRestaurantActivity extends Activity {

	private MapView mapView;
	private BaiduMap simpleBaiduMap; // show position
	private Restaurant restaurant;
	TextView tv_uid,tv_name, tv_addr, tv_type;
	TextView tv_envRate, tv_FctRate, tv_hygRate, tv_servRate, tv_tstRate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temp_restaurant);
//		getActionBar().setDisplayHomeAsUpEnabled(true);

		initRestaurantData();
		initMap();
	}

	private void initMap() {
		mapView = (MapView) findViewById(R.id.simpleMmapView);
		mapView.showZoomControls(false);
		simpleBaiduMap = mapView.getMap();
		
		MapStatusUpdate newMapcenter = MapStatusUpdateFactory.newLatLngZoom(restaurant.getLocation(), 15);
		simpleBaiduMap.animateMapStatus(newMapcenter);

		Marker mMarker = null;
		BitmapDescriptor markerIcon = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		MapUtils.setMarker(mMarker, simpleBaiduMap, restaurant.getLocation(), markerIcon);
	}

	private void initRestaurantData() {
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
