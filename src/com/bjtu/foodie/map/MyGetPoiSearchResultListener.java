package com.bjtu.foodie.map;

import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

public class MyGetPoiSearchResultListener implements
		OnGetPoiSearchResultListener {

	Context context;
	BaiduMap mbaiduMap;
	PoiSearch mPoiSearch;
	
	public MyGetPoiSearchResultListener(Context context, BaiduMap mbaiduMap, PoiSearch mPoiSearch) {
		this.context = context;
		this.mbaiduMap = mbaiduMap;
		this.mPoiSearch = mPoiSearch;
	}
	
	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(context,
					result.getName() + ": " + result.getAddress(),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onGetPoiResult(PoiResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			Toast.makeText(context, "未找到结果", Toast.LENGTH_LONG).show();
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mbaiduMap.clear();

			PoiOverlay overlay = new MyPoiOverlay(mbaiduMap);
			mbaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			overlay.addToMap();
			overlay.zoomToSpan();
			return;
		}
	}

	// get detail when click the restaurant marker of the searching result
	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap arg0) {
			super(arg0);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			PoiInfo poiInfo = getPoiResult().getAllPoi().get(index);
			mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
					.poiUid(poiInfo.uid));
			return true;
		}
	}
}
