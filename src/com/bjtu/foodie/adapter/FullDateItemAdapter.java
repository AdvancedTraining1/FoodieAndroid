package com.bjtu.foodie.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.DateModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FullDateItemAdapter extends BaseAdapter {

	private ArrayList<JSONObject> dates;
	private Context context;

	public FullDateItemAdapter(Context contex, ArrayList<JSONObject> dates) {
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

	/*public String getDateId(int arg0) {
		return dates.get(arg0).getUserId();
	}
*/
	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		View view = arg1;
		Holder holder;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.dates_item,null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}
		
	try{
		final JSONObject date = this.dates.get(position);
		
		holder.tv_item_uname.setText(date.getJSONObject("author").getString("account"));
		
		String friends="Friends: ";
		JSONArray users=date.getJSONArray("dateUsers");
		for(int i=0;i<users.length();i++){
			JSONObject user=users.getJSONObject(i);
			String account=user.getString("account");
			friends=friends+account+".";
		}
		
		holder.tv_item_friend.setText(friends);
		holder.tv_item_time.setText("Time: "+date.getString("dateTime"));
		holder.tv_item_content.setText("Address: "+date.getString("dateContent"));
		holder.tv_item_logtime.setText(date.getString("logTime"));
		
		//---------getPic == id of the pic in drawable-------
		holder.iv_item_userphoto.setImageResource(R.drawable.jay);//date.getJSONObject("author").getString("head")
		holder.btn_item_accept.setOnClickListener(new OnClickJoinBtnListener(position)); //click join
		holder.btn_item_refuse.setOnClickListener(new OnClickJoinBtnListener(position)); //click no join
		
	}catch(Exception e) {
		e.printStackTrace();
	}
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
		TextView tv_item_uname;
		
		TextView tv_item_friend;
		TextView tv_item_time;
		TextView tv_item_content;
		TextView tv_item_logtime;
		
		Button btn_item_accept;
		Button btn_item_refuse;

		public Holder(View view) {
			iv_item_userphoto = (ImageView) view.findViewById(R.id.img_item_userphoto);
			tv_item_uname = (TextView) view.findViewById(R.id.txt_item_uname);
			
			tv_item_friend = (TextView) view.findViewById(R.id.txt_item_friend);
			tv_item_time = (TextView) view.findViewById(R.id.txt_item_time);
			tv_item_content = (TextView) view.findViewById(R.id.txt_item_content);
			tv_item_logtime = (TextView) view.findViewById(R.id.txt_item_logtime);
			
			btn_item_accept = (Button) view.findViewById(R.id.btn_accept);
			btn_item_refuse = (Button) view.findViewById(R.id.btn_refuse);
		}
	}
}
