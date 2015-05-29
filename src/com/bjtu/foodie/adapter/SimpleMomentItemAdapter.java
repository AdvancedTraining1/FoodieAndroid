package com.bjtu.foodie.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjtu.foodie.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class SimpleMomentItemAdapter extends BaseAdapter {

	private List<JSONObject> moments;
	private Context context;
	DisplayImageOptions options;

	public SimpleMomentItemAdapter(Context contex, List<JSONObject> moments) {
		this.moments = moments;
		this.context = contex;
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(0))
		.build();
	}

	@Override
	public int getCount() {
		return moments == null ? 0 : moments.size();
	}

	@Override
	public Object getItem(int arg0) {
		return moments == null ? null : moments.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public String getMomentId(int arg0) {
		return moments.get(arg0).toString();
	}

	@SuppressWarnings({ "static-access" })
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = arg1;
		Holder holder;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.my_moments_item,
					null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}

		JSONObject moment = this.moments.get(arg0);
		try {
			holder.tv_date.setText(moment.getString("date"));
			holder.tv_month.setText("May");
			holder.tv_content.setText(moment.getString("content"));
			ImageLoader.getInstance()
			.displayImage("http://101.200.174.49:3000/"+moment.getString("picture").trim(), holder.iv_item_content_pic, options, new SimpleImageLoadingListener() {
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return view;
	}

	private static class Holder {

		ImageView iv_item_content_pic;
		
		TextView tv_date;
		TextView tv_month;
		TextView tv_content;

		public Holder(View view) {
			tv_date = (TextView) view.findViewById(R.id.tv_Date);
			tv_month = (TextView) view.findViewById(R.id.tv_Month);
			tv_content = (TextView) view
					.findViewById(R.id.tv_content);
			iv_item_content_pic = (ImageView) view
					.findViewById(R.id.iv_photo);
		}
	}
}
