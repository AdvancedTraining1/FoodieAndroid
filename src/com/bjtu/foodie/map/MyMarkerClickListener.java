package com.bjtu.foodie.map;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.bjtu.foodie.R;

public class MyMarkerClickListener implements OnMarkerClickListener {
	
	Context context;
	BaiduMap mbaiduMap;
	PoiSearch mPoiSearch;
	InfoWindow mInfoWindow;

	public MyMarkerClickListener(Context context, BaiduMap mbaiduMap,
			PoiSearch mPoiSearch) {
		super();
		this.context = context;
		this.mbaiduMap = mbaiduMap;
		this.mPoiSearch = mPoiSearch;
	}

	@Override
	public boolean onMarkerClick(final Marker marker) {
		
		final Button popBtn = new Button(context);
		popBtn.setBackgroundResource(R.drawable.popup);
		OnInfoWindowClickListener listener = null;
		popBtn.setText("搜索周边美食");
		popBtn.setTextColor(Color.BLACK);

		listener = new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick() {
				// 显示周边固定范围内到餐厅
				Toast.makeText(context,
						"I am searching around 1000 meters",
						Toast.LENGTH_SHORT).show();
				// 隐藏弹出到气泡按钮
				mbaiduMap.hideInfoWindow();
				
				MapUtils.searchKeyAroundDistance(mPoiSearch, MapUtils.DefaultKeyword,
						marker.getPosition(), MapUtils.DEFAULTDIST,
						new MyGetPoiSearchResultListener(context,
								mbaiduMap, mPoiSearch, marker.getPosition()));
			}
		};
		LatLng ll = marker.getPosition();
		mInfoWindow = new InfoWindow(
				BitmapDescriptorFactory.fromView(popBtn), ll, -47, listener);
		mbaiduMap.showInfoWindow(mInfoWindow);
		return true;
	}

}
