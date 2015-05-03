package com.bjtu.foodie.map;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.bjtu.foodie.R;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption.LocationMode;

public class MapActivity extends Activity {

	// display map
	private MapView baiduMapView = null;
	private BaiduMap mbaiduMap;
	private ActionBar actionBar;
	private boolean curMapModeIsNormal = true; // false => satellite
	
	// location
	private LocationClient mylocClient;
	private LocationMode curLocMode;

	private double longitude, latitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_map);
		actionBar = getActionBar();
		baiduMapView = (MapView) findViewById(R.id.bmapView);
		mbaiduMap = baiduMapView.getMap();

	}

	// locate spot of specific place with icon
	public void markLocation(LatLng curLocation) {
		// build marker icon
		BitmapDescriptor markerIcon = BitmapDescriptorFactory
				.fromResource(R.drawable.ic_action_place);
		// build overlay(MarkerOption) and add the icon on the map
		OverlayOptions overlay = new MarkerOptions().position(curLocation)
				.icon(markerIcon);
		mbaiduMap.addOverlay(overlay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_search:

			break;
		case R.id.action_switchType:
			// set map type [normal and satellite]
			// type: normal = 1(default); satellite = 2
			mbaiduMap.setMapType((mbaiduMap.getMapType()) % 2 + 1);
			if (curMapModeIsNormal) {
				item.setIcon(R.drawable.ic_action_map);
			} else {
				item.setIcon(R.drawable.ic_action_web_site);
			}
			curMapModeIsNormal = !curMapModeIsNormal;
			break;
		case R.id.action_location:
			// BUG: NOT WORKING!!!
			markLocation(getMyCurrentLocation());
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private LatLng getMyCurrentLocation() {
		// turn on the location function
		mbaiduMap.setMyLocationEnabled(true);
		// structure the location data
		// MyLocationData locationData = new MyLocationData.Builder().accuracy()
		Toast.makeText(getApplicationContext(), "locating~~",
				Toast.LENGTH_SHORT).show();
		return null;
	}
}
