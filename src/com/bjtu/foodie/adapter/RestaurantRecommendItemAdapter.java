package com.bjtu.foodie.adapter;

import java.util.List;

import com.baidu.platform.comapi.map.t;
import com.bjtu.foodie.R;
import com.bjtu.foodie.model.Moment;
import com.bjtu.foodie.model.Restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantRecommendItemAdapter extends BaseAdapter {

	private List<Restaurant> restaurants;
	private Context context;

	public RestaurantRecommendItemAdapter(Context context, List<Restaurant> restaurants) {
		this.restaurants = restaurants;
		this.context = context;
	}

	@Override
	public int getCount() {
		return restaurants == null ? 0 : restaurants.size();
	}

	@Override
	public Object getItem(int arg0) {
		return restaurants == null ? null : restaurants.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		View view = arg1;
		Holder holder;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.item_mainpage_recommend,
					null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}
		
		Restaurant r = restaurants.get(position);
		holder.tv_restName.setText(r.getName());
		holder.tv_restType.setText(r.getType());

		return view;
	}
	
	private static class Holder {

		ImageView iv_restphoto;
		TextView tv_restName;
		TextView tv_restType;

		public Holder(View view) {
			iv_restphoto = (ImageView) view.findViewById(R.id.iv_restPhoto);
			tv_restName = (TextView) view.findViewById(R.id.tv_restName);
			tv_restType = (TextView) view.findViewById(R.id.tv_resttype);
		}
	}
}
