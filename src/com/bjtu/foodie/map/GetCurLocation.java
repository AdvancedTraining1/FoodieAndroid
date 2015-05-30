package com.bjtu.foodie.map;

import android.content.Context;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;

public class GetCurLocation {

	// display map
	private Context context;
	private BaiduMap mbaiduMap;

	// location
	private LatLng myCurPosition;
	private LocationClient mlocClient;
	private MyLocationListener locListener;
	private BitmapDescriptor markerIcon;
	private Marker marker;

	public GetCurLocation(Context context, BitmapDescriptor markerIcon,
			BaiduMap mbaiduMap) {
		this.context = context;
		this.mbaiduMap = mbaiduMap;
		this.markerIcon = markerIcon;
		SDKInitializer.initialize(context);
		initLocClient();
	}

	public void initLocClient() {
		// turn on the location function

		mlocClient = new LocationClient(context);
		locListener = new MyLocationListener();

		mlocClient.registerLocationListener(locListener);

		LocationClientOption opt = new LocationClientOption();
		opt.setOpenGps(true);
		opt.setIsNeedAddress(true);
		opt.setScanSpan(1500);
		// coorType - 取值有3个
		// 返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系 ：bd09ll
		opt.setCoorType("bd09ll");
		mlocClient.setLocOption(opt);
		mbaiduMap.setMyLocationEnabled(true);  
        if (!mlocClient.isStarted())  
        {  
            mlocClient.start();  
        }  
		
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location == null) {
				Toast.makeText(context, "location null", Toast.LENGTH_LONG)
						.show();
				return;
			}
			myCurPosition = new LatLng(location.getLatitude(),
					location.getLongitude());
			MapUtils.setLocMarker(marker, mbaiduMap, myCurPosition, markerIcon);
			Toast.makeText(context, myCurPosition.toString(), Toast.LENGTH_LONG)
			.show();

		}
	}

	public void onReceivePoi(BDLocation location) {
	}

}
