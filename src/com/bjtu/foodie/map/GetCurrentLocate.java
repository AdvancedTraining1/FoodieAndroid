package com.bjtu.foodie.map;

import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.bjtu.foodie.UI.MomentAddActivityLast;

public class GetCurrentLocate {

	// display map
	private Context context;

	// location
	private LatLng myCurPosition;
	private LocationClient mlocClient;
	private MyLocationListener locListener;
	private boolean isFirstLoc = true; // if locate first time
	private PoiSearch nearbySearch;

	// my position information
	private static String curAddr;
	private static String curCity; // needed by citySearch
	private ArrayList<String> nearbyNames;

	public GetCurrentLocate(Context context) {
		this.context = context;
		this.nearbyNames = new ArrayList<String>();
		SDKInitializer.initialize(context);

		nearbySearch = PoiSearch.newInstance();
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
		// coorType - 取值有3个
		// 返回国测局经纬度坐标系：gcj02 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系 ：bd09ll
		opt.setCoorType("bd09ll");
		mlocClient.setLocOption(opt);
		mlocClient.start();
		mlocClient.requestLocation();
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location == null) {
				Toast.makeText(context, "location null", Toast.LENGTH_LONG)
						.show();
				return;
			}
			if (isFirstLoc) {
				myCurPosition = new LatLng(location.getLatitude(),
						location.getLongitude());
				isFirstLoc = false;
				curCity = location.getCity();
				curAddr = location.getAddrStr();

				PoiNearbySearchOption opt = new PoiNearbySearchOption();
				opt.location(myCurPosition).keyword("餐厅").radius(1000)
						.sortType(PoiSortType.distance_from_near_to_far);
				Toast.makeText(context, "search !!!!!!!", Toast.LENGTH_SHORT)
						.show();

				if (nearbySearch.searchNearby(opt)) {

					Toast.makeText(context, "search success !!!!!!!",
							Toast.LENGTH_SHORT).show();
					nearbySearch
							.setOnGetPoiSearchResultListener(new MyCurrentNearbyResultListener());
				}
			}
		}

		public void onReceivePoi(BDLocation location) {
		}
	}

	class MyCurrentNearbyResultListener implements OnGetPoiSearchResultListener {

		@Override
		public void onGetPoiDetailResult(PoiDetailResult result) {

		}

		@Override
		public void onGetPoiResult(PoiResult result) {

			Toast.makeText(context, "getPoiResult already", Toast.LENGTH_LONG)
					.show();
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(context, "未找到结果", Toast.LENGTH_LONG).show();
				return;
			}
			if (result.error == SearchResult.ERRORNO.NO_ERROR) {

				for (PoiInfo info : result.getAllPoi()) {
					nearbyNames.add(info.name);
				}
				MomentAddActivityLast.showNearbyList(nearbyNames);
			}
		}

	}

	static public String getCurAddr() {
		return curAddr;
	}

	static public String getCurCity() {
		return curCity;
	}

}
