package com.bjtu.foodie.map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.bjtu.foodie.R;

public class MapActivity extends Activity {

	// display map
	private MapView baiduMapView = null;
	private BaiduMap mbaiduMap;
	private Context context;
	private boolean curMapModeIsNormal = true; // false => satellite

	// surround
	private PoiSearch mPoiSearchAround = null;
	private Marker curLocMarker;
	private InfoWindow mInfoWindow;

	// location
	private LatLng myCurPosition = null;
	private LocationClient mlocClient;
	private LocationMode curLocMode; // default
	private BitmapDescriptor markerIcon;
	private MyLocationListener locListener;
	private boolean isFirstLoc = true; // if locate first time
	private float curZoom = 16;

	// search around
	private EditText et_searchKeyword;
	private EditText et_searchDistance;
	private ImageButton ib_search;
	private LinearLayout ll_search;
	
	// my position infomation
	private static String curAddr;
	private static String curCity; //  needed by citySearch

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		SDKInitializer.initialize(context);
		setContentView(R.layout.activity_map);

		context = getApplicationContext();

		// initial map
		baiduMapView = (MapView) findViewById(R.id.bmapView);

		baiduMapView.showZoomControls(false);
		mbaiduMap = baiduMapView.getMap();
		mPoiSearchAround = PoiSearch.newInstance();
		initLocClient();
		mbaiduMap.setOnMarkerClickListener(new MyMarkerClickListener(context,
				mbaiduMap, mPoiSearchAround));
	}

	private void initLocClient() {
		// turn on the location function
		mbaiduMap.setMyLocationEnabled(true);

		mlocClient = new LocationClient(context);
		locListener = new MyLocationListener();

		// build marker icon
		markerIcon = BitmapDescriptorFactory
				.fromResource(R.drawable.icon_location_red);
		curLocMode = LocationMode.NORMAL;
		mbaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
		// LocationMode mode, boolean enableDirection, BitmapDescriptor
		// customMarker)
				curLocMode, true, markerIcon));
		mlocClient.registerLocationListener(locListener);

		LocationClientOption opt = new LocationClientOption();
		opt.setOpenGps(true);
		opt.setIsNeedAddress(true);
		// coorType - 取值有3个
		// 返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系 ：bd09ll
		opt.setCoorType("bd09ll");
		opt.setScanSpan(2000); // 设置扫描间隔，单位是毫秒 当<1000(1s)时，定时定位无效
		mlocClient.setLocOption(opt);
		mlocClient.start();
		mlocClient.requestLocation();
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
			getSearchDialog();
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
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void getSearchDialog() {

		ll_search = (LinearLayout) findViewById(R.id.ll_searchAround);
		ll_search.setVisibility(0);

		et_searchKeyword = (EditText) findViewById(R.id.et_searchKeyWord);
		et_searchDistance = (EditText) findViewById(R.id.et_distance);
		ib_search = (ImageButton) findViewById(R.id.ib_search);

		// curloc got some problem can not get curPosition and have
		// nullpointerException
		ib_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int distance = 0;
				String distanceS = et_searchDistance.getText().toString();
				String key = et_searchKeyword.getText().toString();
				if (distanceS.isEmpty()) {
					Toast.makeText(context, "empty distance",
							Toast.LENGTH_SHORT).show();
				} else if (key.isEmpty()) {
					Toast.makeText(context, "empty keyword", Toast.LENGTH_SHORT)
							.show();
				} else {
					distance = Integer.valueOf(distanceS);

					if (distance < 0) {
						Toast.makeText(context, "illegal distance value",
								Toast.LENGTH_SHORT).show();
					} else {
						String s = "keyword:" + key + "  distance:" + distance;
						Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

						mbaiduMap.clear();
						MapUtils.searchKeyAroundDistance(mPoiSearchAround, key,
								myCurPosition, distance,
								new MyGetPoiSearchResultListener(context,
										mbaiduMap, mPoiSearchAround, myCurPosition));
					}
				}
			}
		});
	}

	private void markMyCurPosition() {
		mbaiduMap.clear();
		// set new center point and zoom level(3-20) of the map
		MapStatusUpdate newState = MapStatusUpdateFactory
				.newLatLngZoom(myCurPosition, curZoom);
		mbaiduMap.animateMapStatus(newState);
		MapUtils.setMarker(curLocMarker, mbaiduMap, myCurPosition, markerIcon);
		Toast.makeText(context, "my city ->" + curCity, Toast.LENGTH_LONG).show();
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location == null || mbaiduMap == null)
				return;
			if (isFirstLoc) {
				myCurPosition = new LatLng(location.getLatitude(),
						location.getLongitude());
				isFirstLoc = false;
				curCity = location.getCity();
				curAddr = location.getAddrStr();
				markMyCurPosition();
			} 
		}

		public void onReceivePoi(BDLocation location) {
		}
	}

	static public String getCurAddr() {
		return curAddr;
	}

	static public String getCurCity() {
		return curCity;
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

		mlocClient.unRegisterLocationListener(locListener);
		mlocClient.stop();
		mbaiduMap.setMyLocationEnabled(false);
		baiduMapView.onDestroy();
		baiduMapView = null;
		super.onDestroy();
	}

}
