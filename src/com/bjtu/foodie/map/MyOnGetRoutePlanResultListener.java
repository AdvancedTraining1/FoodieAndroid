package com.bjtu.foodie.map;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

public class MyOnGetRoutePlanResultListener implements OnGetRoutePlanResultListener{

	// get from MapActivity
	private String stName; 
	private String endName;
	
	private BaiduMap mbaiduMap;
	
	
	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
		// TODO Auto-generated method stub
		
	}

}
