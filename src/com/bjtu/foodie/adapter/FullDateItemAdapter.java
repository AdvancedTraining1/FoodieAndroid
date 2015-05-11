package com.bjtu.foodie.adapter;

import java.util.List;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.DateModel;

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

public class FullDateItemAdapter extends BaseAdapter {

	private List<DateModel> dates;
	private Context context;

	public FullDateItemAdapter(Context contex, List<DateModel> dates) {
		this.dates = dates;
		this.context = contex;
	}

	@Override
	public int getCount() {
		return dates == null ? 0 : dates.size();
	}

	@Override
	public Object getItem(int arg0) {
		return dates == null ? null : dates.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public String getDateId(int arg0) {
		return dates.get(arg0).getUserId();
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		View view = arg1;
		Holder holder;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.moments_item,//dates_item is or not
					null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}

		DateModel date = this.dates.get(position);
		holder.tv_item_uname.setText(date.getUserName());
		holder.tv_item_content.setText(date.getDateContent());
		holder.tv_item_time.setText(date.getSimpleDate());
		
		//---------getPic == id of the pic in drawable-------
		holder.iv_item_userphoto.setImageResource(date.getUserPic());
		//holder.iv_item_content_pic.setImageResource(moment.getPic());
		holder.ib_item_comment.setOnClickListener(new OnClickJoinBtnListener(position)); //click join
		return view;
	}
	
	private class OnClickJoinBtnListener implements OnClickListener {
		private int pos;
		
		public OnClickJoinBtnListener(int pos) {
			this.pos = pos;
		}
		@Override
		public void onClick(View v) {
			Toast.makeText(context, "On click item" + pos, Toast.LENGTH_SHORT).show();
		}
	}

	private static class Holder {

		ImageView iv_item_userphoto;
		//ImageView iv_item_content_pic;
		ImageButton ib_item_comment;

		TextView tv_item_uname;
		TextView tv_item_time;
		TextView tv_item_content;

		public Holder(View view) {
			tv_item_uname = (TextView) view.findViewById(R.id.txt_item_uname);
			tv_item_time = (TextView) view.findViewById(R.id.txt_item_time);
			tv_item_content = (TextView) view
					.findViewById(R.id.txt_item_content);
			iv_item_userphoto = (ImageView) view
					.findViewById(R.id.img_item_userphoto);
			//iv_item_content_pic = (ImageView) view
			//		.findViewById(R.id.img_item_content_pic);
			ib_item_comment = (ImageButton) view.findViewById(R.id.imgBtn_comment);
		}
	}
}
