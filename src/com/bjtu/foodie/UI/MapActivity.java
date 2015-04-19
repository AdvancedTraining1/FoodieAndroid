package com.bjtu.foodie.UI;

import org.apache.http.conn.scheme.LayeredSocketFactory;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
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

public class MapActivity extends Activity {

	private MapView baiduMapView = null;
	private BaiduMap mbaiduMap;
	private ActionBar actionBar;
	private int clickTime = 0; // remember the clicktime is even or odd

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

	public LatLng getLocation() {
		getApplicationContext();
		LocationManager mLocationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime,
		// minDistance, listener)
		mLocationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				0, 0, new LocationListener() {

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
					}

					@Override
					public void onProviderEnabled(String provider) {
					}

					@Override
					public void onProviderDisabled(String provider) {
					}

					@Override
					public void onLocationChanged(Location location) {
						longitude = location.getLongitude();
						latitude = location.getLatitude();
						Toast.makeText(
								getApplicationContext(),
								"Longitude : " + longitude + "; Latitude : "
										+ latitude, Toast.LENGTH_SHORT).show();
					}
				});
//		return (new LatLng(39.963175, 116.400244));
		return (new LatLng(longitude, latitude));
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
			if (clickTime == 0) {
				item.setIcon(R.drawable.ic_action_map);
				clickTime++;
			} else {
				item.setIcon(R.drawable.ic_action_web_site);
				clickTime--;
			}
			break;
		case R.id.action_location:
			// BUG: NOT WORKING!!!
			markLocation(getLocation());
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void getMyCurrentLocation() {
		// turn on the location function
		mbaiduMap.setMyLocationEnabled(true);
		// structure the location data
		// MyLocationData locationData = new MyLocationData.Builder().accuracy()
		Toast.makeText(getApplicationContext(), "locating~~",
				Toast.LENGTH_SHORT).show();
	}
}
