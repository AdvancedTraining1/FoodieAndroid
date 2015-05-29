package com.bjtu.foodie.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.bjtu.foodie.R;
import com.bjtu.foodie.common.Constants;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class SimpleMomentLikeAdapter extends BaseAdapter{
	DisplayImageOptions options;
	List<JSONObject> list;
	private Context context;
	
	public SimpleMomentLikeAdapter(Context context, ArrayList<JSONObject> list) {
		this.context = context;
		this.list = list;
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(90))
		.build();
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	public JSONObject getItem(int positon){
		return list == null ? null : list.get(positon);
	}
	
	@Override
	public long getItemId(int position){
		return position;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		View view = arg1;
		Holder holder;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.moment_like_item, null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}
		JSONObject oneComment = this.list.get(position);
		
		try {
			ImageLoader.getInstance()
			.displayImage("http://101.200.174.49:3000/"+oneComment.getString("head").trim(), holder.userImage, options, new SimpleImageLoadingListener() {
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
		holder.userImage.setOnClickListener(new OnClickUserListener(position));
		return view;
	}
	
	private class OnClickUserListener implements OnClickListener {
		private int pos;
		private String momentId;
		
		public OnClickUserListener(int pos) {
			this.pos = pos;
		}
		@Override
		public void onClick(View v) {
			Log.i(Constants.TAG_MOMENT, "click"+pos);
		}
	}

	private static class Holder {
		ImageView userImage;
		public Holder(View view) {
			userImage = (ImageView) view.findViewById(R.id.moment_like_user);
		}
	}
}
