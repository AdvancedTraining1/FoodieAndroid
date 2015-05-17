package com.bjtu.foodie.map;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.bjtu.foodie.R;

public class RoutePlanActivity extends Activity implements
		OnGetRoutePlanResultListener {

	private String curCity;
	private String stAddr, enAddr;
	private LatLng stLoc, enLoc;

	private TextView tv_start;
	private TextView tv_end;
	private ImageView iv_relocate;
	private ImageView iv_refind;
	private ImageView iv_walk, iv_bus, iv_car;

	private MapView mapView;
	private BaiduMap mbaiduMap;
	private RoutePlanSearch mRoutePlanSearch;

	private List<DrivingRouteLine> carRouteLines;
	private List<TransitRouteLine> busRouteLines;
	private List<WalkingRouteLine> walkRouteLines;
	private DrivingRouteLine curCarRoute;
	private TransitRouteLine curBusRoute;
	private WalkingRouteLine curWalkRoute;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_plan);

		initView();
		initMap();

		// initial startNode and endNode
		final PlanNode startNode = PlanNode.withLocation(stLoc);
		final PlanNode endNode = PlanNode.withLocation(enLoc);

		iv_walk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mRoutePlanSearch.walkingSearch(new WalkingRoutePlanOption()
						.from(startNode).to(endNode));
			}
		});
		iv_bus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mRoutePlanSearch.transitSearch(new TransitRoutePlanOption()
						.city(curCity).from(startNode).to(endNode));
			}
		});
		iv_car.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mRoutePlanSearch.drivingSearch(new DrivingRoutePlanOption()
						.from(startNode).to(endNode));
			}
		});
	}

	private void initMap() {
		mbaiduMap = mapView.getMap();
		mapView.showZoomControls(false);
		// routePlanSearch register and set listener
		mRoutePlanSearch = RoutePlanSearch.newInstance();
		mRoutePlanSearch.setOnGetRoutePlanResultListener(this);

		Marker markerS = null, markerE = null;
		MapUtils.setMarker(markerS, mbaiduMap, stLoc, BitmapDescriptorFactory
				.fromResource(R.drawable.icon_selflocation));
		MapUtils.setMarker(markerE, mbaiduMap, enLoc, BitmapDescriptorFactory
				.fromResource(R.drawable.icon_restaurant));

		MapStatusUpdate newStatus = MapStatusUpdateFactory.newLatLngZoom(stLoc,
				16);
		mbaiduMap.animateMapStatus(newStatus);
	}

	private void initView() {
		tv_start = (TextView) findViewById(R.id.tv_startPos);
		tv_end = (TextView) findViewById(R.id.tv_endPos);
		iv_relocate = (ImageView) findViewById(R.id.iv_reLocation);
		iv_refind = (ImageView) findViewById(R.id.iv_refindResaurant);
		iv_walk = (ImageView) findViewById(R.id.iv_walk);
		iv_bus = (ImageView) findViewById(R.id.iv_bus);
		iv_car = (ImageView) findViewById(R.id.iv_car);

		mapView = (MapView) findViewById(R.id.baiduMap);

		Intent intentfromMap = getIntent();
		curCity = intentfromMap.getExtras().getString("curCity");
		stAddr = intentfromMap.getExtras().getString("startPos");
		enAddr = intentfromMap.getExtras().getString("endPos");
		double[] stF = intentfromMap.getDoubleArrayExtra("startPosition");
		double[] enF = intentfromMap.getExtras().getDoubleArray("endPosition");
		Toast.makeText(getApplicationContext(), stF[0] + " " + stF[1],
				Toast.LENGTH_LONG).show();
		stLoc = new LatLng(stF[0], stF[1]);
		enLoc = new LatLng(enF[0], enF[1]);

		tv_start.setText(stAddr);
		tv_end.setText(enAddr);
	}

	@Override
	public void onGetDrivingRouteResult(DrivingRouteResult driveResult) {
		Toast.makeText(getApplicationContext(), "car plan", Toast.LENGTH_SHORT)
				.show();

		if (driveResult == null
				|| driveResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(getApplicationContext(), "No Route",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (driveResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			Toast.makeText(getApplicationContext(),
					"ambiguous end or start address", Toast.LENGTH_LONG).show();
		}
		if (driveResult.error == SearchResult.ERRORNO.NO_ERROR) {
			carRouteLines = driveResult.getRouteLines();
			curCarRoute = carRouteLines.get(0);
			Toast.makeText(getApplicationContext(),
					"how many routes:" + carRouteLines.size(),
					Toast.LENGTH_LONG).show();
			mbaiduMap.clear();
			DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mbaiduMap);
			mbaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(curCarRoute);
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	@Override
	public void onGetTransitRouteResult(TransitRouteResult busResult) {
		Toast.makeText(getApplicationContext(), "bus plan", Toast.LENGTH_SHORT)
				.show();
		if (busResult == null
				|| busResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(getApplicationContext(), "No Route",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (busResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			Toast.makeText(getApplicationContext(),
					"ambiguous end or start address", Toast.LENGTH_LONG).show();
			return;
		}
		if (busResult.error == SearchResult.ERRORNO.ST_EN_TOO_NEAR) {
			Toast.makeText(getApplicationContext(), "Too close",
					Toast.LENGTH_LONG).show();
			return;

		}
		if (busResult.error == SearchResult.ERRORNO.NOT_SUPPORT_BUS) {
			Toast.makeText(getApplicationContext(), "No bus to there",
					Toast.LENGTH_LONG).show();
			return;
		}
		if (busResult.error == SearchResult.ERRORNO.NO_ERROR) {
			busRouteLines = busResult.getRouteLines();
			curBusRoute = busRouteLines.get(0);
//			Toast.makeText(getApplicationContext(),
			/** bugs:NullPointerException */
//					"how many routes:" + walkRouteLines.size(),
//					Toast.LENGTH_LONG).show();
			TransitRouteOverlay overlay = new MyTransitRouteOverlay(mbaiduMap);
			mbaiduMap.clear();
			mbaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(curBusRoute);
			overlay.addToMap();
			overlay.zoomToSpan();
		}
	}

	@Override
	public void onGetWalkingRouteResult(WalkingRouteResult walkResult) {
		Toast.makeText(getApplicationContext(), "walk plan", Toast.LENGTH_SHORT)
				.show();
		if (walkResult == null
				|| walkResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(getApplicationContext(), "No Route",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (walkResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
			Toast.makeText(getApplicationContext(),
					"ambiguous end or start address", Toast.LENGTH_LONG).show();
		}
		if (walkResult.error == SearchResult.ERRORNO.NO_ERROR) {
			walkRouteLines = walkResult.getRouteLines();
			curWalkRoute = walkRouteLines.get(0);

			Toast.makeText(getApplicationContext(),
					"how many routes:" + walkRouteLines.size(),
					Toast.LENGTH_LONG).show();
			mbaiduMap.clear();
			WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mbaiduMap);
			mbaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(curWalkRoute);
			overlay.addToMap();
			overlay.zoomToSpan();
		}

	}

	class MyDrivingRouteOverlay extends DrivingRouteOverlay {

		public MyDrivingRouteOverlay(BaiduMap arg0) {
			super(arg0);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_selflocation);
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_restaurant);
		}
	}

	class MyTransitRouteOverlay extends TransitRouteOverlay {

		public MyTransitRouteOverlay(BaiduMap arg0) {
			super(arg0);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_selflocation);
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_restaurant);
		}
	}

	class MyWalkingRouteOverlay extends WalkingRouteOverlay {

		public MyWalkingRouteOverlay(BaiduMap arg0) {
			super(arg0);
		}

		@Override
		public BitmapDescriptor getStartMarker() {
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_selflocation);
		}

		@Override
		public BitmapDescriptor getTerminalMarker() {
			return BitmapDescriptorFactory
					.fromResource(R.drawable.icon_restaurant);
		}
	}
}
