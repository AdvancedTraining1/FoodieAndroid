package com.bjtu.foodie.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bjtu.foodie.R;
import com.bjtu.foodie.UI.LoginActivity;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.model.DateModel;
import com.bjtu.foodie.model.User;
import com.bjtu.foodie.utils.DatesTalkToServer;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FullMyDateItemAdapter extends BaseAdapter {

	private ArrayList<JSONObject> dates;
	private Context context;
	
	//public UserDao userDao=new UserDao(context);;
	private Handler handler = new Handler();

	public FullMyDateItemAdapter(Context contex, ArrayList<JSONObject> dates) {
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
		final Holder holder;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.mydates_item,null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}
		
	try{
		final JSONObject date = this.dates.get(position);
		
		String accept="Accept: ";
		String refuse="Refuse: ";
		JSONArray users=date.getJSONArray("dateUsers");
		
		for(int i=0;i<users.length();i++){
			JSONObject user=users.getJSONObject(i);
			if(user.getString("status").equals("1")){
				String account=user.getString("account");
				accept=accept+account+".";
			}
			
		}
		holder.tv_item_accept.setText(accept);
		
		
		for(int i=0;i<users.length();i++){
			JSONObject user=users.getJSONObject(i);
			if(user.getString("status").equals("2")){
				String account=user.getString("account");
				refuse=refuse+account+".";
			}
			
		}
		holder.tv_item_refuse.setText(refuse);
		
		
		String friends="Friends: ";
		//JSONArray users=date.getJSONArray("dateUsers");
		for(int i=0;i<users.length();i++){
			JSONObject user=users.getJSONObject(i);
			String account=user.getString("account");
			friends=friends+account+".";
		}		
		holder.tv_item_friend.setText(friends);
		
		holder.tv_item_time.setText("Time: "+date.getString("dateTime"));
		holder.tv_item_content.setText("Address: "+date.getString("dateContent"));
		holder.tv_item_logtime.setText(date.getString("logTime"));
		
	}catch(Exception e) {
		e.printStackTrace();
	}
		return view;
	}

	private static class Holder {

		TextView tv_item_accept;
		TextView tv_item_refuse;
		
		TextView tv_item_friend;
		TextView tv_item_time;
		TextView tv_item_content;
		TextView tv_item_logtime;
		

		public Holder(View view) {
			tv_item_accept = (TextView) view.findViewById(R.id.txt_item_accept);
			tv_item_refuse = (TextView) view.findViewById(R.id.txt_item_refuse);
			
			tv_item_friend = (TextView) view.findViewById(R.id.txt_item_friend);
			tv_item_time = (TextView) view.findViewById(R.id.txt_item_time);
			tv_item_content = (TextView) view.findViewById(R.id.txt_item_content);
			tv_item_logtime = (TextView) view.findViewById(R.id.txt_item_logtime);
			
		}
	}
}
