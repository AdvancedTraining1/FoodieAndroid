package com.bjtu.foodie.map;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;

public class MapUtils {

	public static final int DEFAULTDIST = 1000;
	public static final String DefaultKeyword = "餐厅";

	public static void drawNearByCircle(BaiduMap mbaiduMap, LatLng center,
			int radius) {
		// mbaiduMap.clear();
		OverlayOptions circleOverlay = new CircleOptions().center(center)
				.radius(radius).fillColor(0x33A8A8A8);
		MapStatusUpdate newState = MapStatusUpdateFactory.newLatLng(center);
		mbaiduMap.animateMapStatus(newState);
		mbaiduMap.addOverlay(circleOverlay);
	}

	public static void searchKeyAroundDistance(PoiSearch mPoiSearch,
			String key, LatLng position, int distance,
			OnGetPoiSearchResultListener listener) {
		PoiNearbySearchOption curNearByOption = (new PoiNearbySearchOption())
				.keyword(key).location(position).radius(distance)
				.pageCapacity(50);
		if (mPoiSearch.searchNearby(curNearByOption)) {
			mPoiSearch.setOnGetPoiSearchResultListener(listener);
		}
	}

	public static void setMarker(Marker curLocMarker, BaiduMap mbaiduMap,
			LatLng location, BitmapDescriptor markerIcon) {
		OverlayOptions curLocOO = new MarkerOptions()
				.position(new LatLng(location.latitude, location.longitude))
				.icon(markerIcon).zIndex(9).draggable(true);
		curLocMarker = (Marker) mbaiduMap.addOverlay(curLocOO);
	}

}
