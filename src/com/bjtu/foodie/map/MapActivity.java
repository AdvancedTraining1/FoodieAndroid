package com.bjtu.foodie.map;

import org.apache.http.util.LangUtils;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.bjtu.foodie.R;

public class MapActivity extends Activity {

	// display map
	private MapView baiduMapView = null;
	private BaiduMap mbaiduMap;
	private ActionBar actionBar;
	private boolean curMapModeIsNormal = true; // false => satellite

	// surround
	private PoiSearch mPoiSearchAround = null;
	private Marker curLocMarker;
	private InfoWindow mInfoWindow;

	// location
	private LocationClient mlocClient;
	private LocationMode curLocMode; // default
	private BitmapDescriptor markerIcon;
	private MyLocationListener locListener;
	private boolean isFirstLoc = true; // if locate first time

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_map);

		actionBar = getActionBar();

		// initial map
		baiduMapView = (MapView) findViewById(R.id.bmapView);
		baiduMapView.showZoomControls(false);
		mbaiduMap = baiduMapView.getMap();
		mPoiSearchAround = PoiSearch.newInstance();
		initLocClient();
		mbaiduMap.setOnMarkerClickListener(new myMarkerClickListener());
	}

	private void initLocClient() {
		// turn on the location function
		mbaiduMap.setMyLocationEnabled(true);

		mlocClient = new LocationClient(getApplicationContext());
		locListener = new MyLocationListener();

		// build marker icon
		markerIcon = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_gcoding);
		curLocMode = LocationMode.NORMAL;
		mbaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
		// LocationMode mode, boolean enableDirection, BitmapDescriptor
		// customMarker)
				curLocMode, true, markerIcon));
		mlocClient.registerLocationListener(locListener);

		LocationClientOption opt = new LocationClientOption();
		opt.setOpenGps(true);
		// coorType - 取值有3个
		// 返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系 ：bd09ll
		opt.setCoorType("bd09ll");
		opt.setScanSpan(2000); // 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
		mlocClient.setLocOption(opt);
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
			getMyCurrentLocation();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void getMyCurrentLocation() {
		mlocClient.start();
		mlocClient.requestLocation();
	}

	private void getMyAround(BDLocation location) {

	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location == null || mbaiduMap == null)
				return;
			// get current location data
			// MyLocationData locationData = new MyLocationData.Builder()
			// .accuracy(location.getRadius()).direction(0)
			// .latitude(location.getLatitude())
			// .longitude(location.getLongitude()).build();
			// mbaiduMap.setMyLocationData(locationData);
			if (isFirstLoc) {
				isFirstLoc = false;
				// set new center point and zoom level(3-20) of the map
				MapStatusUpdate newState = MapStatusUpdateFactory
						.newLatLngZoom((new LatLng(location.getLatitude(),
								location.getLongitude())), 15);
				mbaiduMap.animateMapStatus(newState);
				setMarker(location);
			}
		}

		private void setMarker(BDLocation location) {
			OverlayOptions curLocOO = new MarkerOptions()
					.position(
							new LatLng(location.getLatitude(), location
									.getLongitude())).icon(markerIcon)
					.zIndex(9).draggable(true);
			curLocMarker = (Marker) mbaiduMap.addOverlay(curLocOO);
		}

		public void onReceivePoi(BDLocation location) {
		}
	}

	class myMarkerClickListener implements OnMarkerClickListener {

		@Override
		public boolean onMarkerClick(final Marker marker) {
			Button popBtn = new Button(getApplicationContext());
			popBtn.setBackgroundResource(R.drawable.popup);
			OnInfoWindowClickListener listener = null;
			popBtn.setText("搜索周边美食");
			popBtn.setTextColor(Color.BLACK);

			listener = new OnInfoWindowClickListener() {
				@Override
				public void onInfoWindowClick() {
					// 显示周边固定范围内到餐厅
					Toast.makeText(getApplicationContext(), "I am searching",
							Toast.LENGTH_SHORT).show();
					PoiNearbySearchOption curNearByOption = 
							new PoiNearbySearchOption().keyword("餐厅").location(marker.getPosition()).radius(1000);
					if(mPoiSearchAround.searchNearby(curNearByOption)) {
						;
					}
				}
			};
			LatLng ll = marker.getPosition();
			mInfoWindow = new InfoWindow(
					BitmapDescriptorFactory.fromView(popBtn), ll, -47, listener);
			mbaiduMap.showInfoWindow(mInfoWindow);
			return true;
		}

	}

	@Override
	protected void onPause() {
		baiduMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		baiduMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {

		mlocClient.stop();
		mbaiduMap.setMyLocationEnabled(false);
		baiduMapView.onDestroy();
		baiduMapView = null;
		super.onDestroy();
	}
}
