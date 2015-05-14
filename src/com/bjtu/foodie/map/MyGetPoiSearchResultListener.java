package com.bjtu.foodie.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.bjtu.foodie.R;
import com.bjtu.foodie.TempRestaurantActivity;
import com.bjtu.foodie.model.Restaurant;
import com.bjtu.foodie.model.RestaurantForSerializable;

public class MyGetPoiSearchResultListener implements
		OnGetPoiSearchResultListener {

	Context context;
	BaiduMap mbaiduMap;
	PoiSearch mPoiSearch;
	LatLng position;
	InfoWindow mInfoWindow;

	public MyGetPoiSearchResultListener(Context context, BaiduMap mbaiduMap,
			PoiSearch mPoiSearch, LatLng position) {
		this.context = context;
		this.mbaiduMap = mbaiduMap;
		this.mPoiSearch = mPoiSearch;
		this.position = position;
	}

	@Override
	public void onGetPoiDetailResult(final PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(context, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		} else {
			mbaiduMap.hideInfoWindow();
			String info = "UID: " + result.getUid() + "\nname: "
					+ result.getName() + "\nAdress:" + result.getAddress()
					+ "\n营业时间:" + result.getShopHours() + "\n餐厅类型:"
					+ result.getType() + "\n环境评分:"
					+ result.getEnvironmentRating() + "\n人均价格:"
					+ result.getPrice() + "\n服务评分:" + result.getServiceRating();
			// click and pop the detail infos of restaurant
			TextView tv_restrInfodetail = new TextView(context);
			tv_restrInfodetail.setBackgroundResource(R.drawable.location_tips);
			tv_restrInfodetail.setText(info);
			tv_restrInfodetail.setWidth(500);

			final RestaurantForSerializable curRest = new RestaurantForSerializable(
					result.getUid(), result.getName(), result.getPrice(),
					result.getAddress(), result.getTelephone(),
					result.getShopHours(), result.getTag(),
					result.getOverallRating(), result.getEnvironmentRating(),
					result.getFacilityRating(), result.getHygieneRating(),
					result.getServiceRating(), result.getTasteRating());
			
			OnInfoWindowClickListener listener = new OnInfoWindowClickListener() {

				@Override
				public void onInfoWindowClick() {
					// Toast.makeText(context, "intent to detail page",
					// Toast.LENGTH_LONG).show();
					Intent intentToRest = new Intent(context,
							TempRestaurantActivity.class);
					Bundle restInfoBundle = new Bundle();
					// putSerializable需要传递数据所有到类型都实现了serializable接口
					// 百度自定义LatLng类没有实现该接口，需要单独传
					restInfoBundle.putSerializable("restauInfo", curRest);
					intentToRest.putExtras(restInfoBundle);
					intentToRest.putExtra("latitude", result.getLocation().latitude);
					intentToRest.putExtra("longitude", result.getLocation().longitude);
					intentToRest.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					context.startActivity(intentToRest);
				}
			};
			LatLng ll = result.getLocation();
			mInfoWindow = new InfoWindow(
					BitmapDescriptorFactory.fromView(tv_restrInfodetail), ll,
					-27, listener);
			mbaiduMap.showInfoWindow(mInfoWindow);
			MapStatusUpdate newStatus = MapStatusUpdateFactory.newLatLng(ll);
			mbaiduMap.animateMapStatus(newStatus);
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

			MapUtils.drawNearByCircle(mbaiduMap, position, MapUtils.DEFAULTDIST);
			String rN = "currentPageCapacity:"
					+ result.getCurrentPageCapacity() + "\nTotalNumber:"
					+ result.getTotalPoiNum();
			Toast.makeText(context, rN, Toast.LENGTH_LONG).show();
			PoiOverlay overlay = new MyPoiOverlay(mbaiduMap);
			mbaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			overlay.addToMap();
			overlay.zoomToSpan();
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
