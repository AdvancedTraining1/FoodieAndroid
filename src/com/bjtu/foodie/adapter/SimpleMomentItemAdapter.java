package com.bjtu.foodie.adapter;

import java.util.List;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.Moment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleMomentItemAdapter extends BaseAdapter {

	private List<Moment> moments;
	private Context context;

	public SimpleMomentItemAdapter(Context contex, List<Moment> moments) {
		this.moments = moments;
		this.context = contex;
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
		return moments.get(arg0).getId();
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

		Moment moment = this.moments.get(arg0);
		holder.tv_date.setText(moment.getDate().getDay()+" ");
		holder.tv_month.setText(moment.getMonth());
		holder.tv_content.setText(moment.getContent());
		
		//---------getPic == id of the pic in drawable-------
		holder.iv_item_content_pic.setImageResource(moment.getPic());
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
