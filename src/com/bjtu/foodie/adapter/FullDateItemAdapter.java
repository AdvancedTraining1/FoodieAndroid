package com.bjtu.foodie.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.bjtu.foodie.R;
import com.bjtu.foodie.UI.LoginActivity;
import com.bjtu.foodie.common.Constants;
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

public class FullDateItemAdapter extends BaseAdapter {

	private ArrayList<JSONObject> dates;
	private Context context;
	
	//public UserDao userDao=new UserDao(context);;
	private Handler handler = new Handler();
	
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
		final Holder holder;
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
			
			if(user.getString("status").equals("1")){//accept
				holder.btn_item_accept.setVisibility(View.GONE);
				holder.btn_item_refuse.setVisibility(View.GONE);
				
				holder.tv_status.setVisibility(View.VISIBLE);
				holder.tv_status.setText("have accepted");
				
			}else if(user.getString("status").equals("2")){//refuse
				holder.btn_item_accept.setVisibility(View.GONE);
				holder.btn_item_refuse.setVisibility(View.GONE);
				
				holder.tv_status.setVisibility(View.VISIBLE);
				holder.tv_status.setText("have refused");
				
			}else{
				holder.btn_item_accept.setVisibility(View.VISIBLE);
				holder.btn_item_refuse.setVisibility(View.VISIBLE);
				holder.tv_status.setVisibility(View.GONE);
				
				
				holder.btn_item_accept.setOnClickListener(new OnClickListener(){

					public void onClick(View v){
						try {										
							final String message;
							String urlString = "date/join";

							List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
							postParameters.add(new BasicNameValuePair("dateId", date.getString("_id")));	
							postParameters.add(new BasicNameValuePair("token", LoginActivity.token));
							String ListResult = DatesTalkToServer.datesPost(urlString, postParameters);
							JSONObject jsonObject = new JSONObject(ListResult);
							message = jsonObject.getString("message");
							// 子线程不能修改main线程，所以必须用handler来对ui线程进行操作
							handler.post(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
								}
							});
							
							if(message.equals("join successful！")){
								
								holder.btn_item_accept.setVisibility(View.GONE);
								holder.btn_item_refuse.setVisibility(View.GONE);
								
								holder.tv_status.setVisibility(View.VISIBLE);
								holder.tv_status.setText("have accepted");
								
							}
																				
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
				
				holder.btn_item_refuse.setOnClickListener(new OnClickListener(){
					public void onClick(View v){
						try {
							
							final String message;
							String urlString = "date/nojoin";

							List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
							postParameters.add(new BasicNameValuePair("dateId", date.getString("_id")));	
							postParameters.add(new BasicNameValuePair("token", LoginActivity.token));
							String ListResult = DatesTalkToServer.datesPost(urlString, postParameters);
							JSONObject jsonObject = new JSONObject(ListResult);
							message = jsonObject.getString("message");
							// 子线程不能修改main线程，所以必须用handler来对ui线程进行操作
							handler.post(new Runnable() {
								@Override
								public void run() {
									Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
								}
							});
							
							if(message.equals("refuse successful！")){
								
								holder.btn_item_accept.setVisibility(View.GONE);
								holder.btn_item_refuse.setVisibility(View.GONE);
								
								holder.tv_status.setVisibility(View.VISIBLE);
								holder.tv_status.setText("have refused");
								
							}
																				
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
			}
		}
		
		holder.tv_item_friend.setText(friends);
		holder.tv_item_time.setText("Time: "+date.getString("dateTime"));
		holder.tv_item_content.setText("Address: "+date.getString("dateContent"));
		holder.tv_item_logtime.setText(date.getString("logTime"));
		
		//---------getPic == id of the pic in drawable-------
		holder.iv_item_userphoto.setImageResource(R.drawable.jay);//date.getJSONObject("author").getString("head")
		
		
		/*if(Constants.flagAccept.equals("accept")&&Constants.flagRefuse.equals("refuse")){
			holder.btn_item_accept.setVisibility(View.VISIBLE);
			holder.btn_item_refuse.setVisibility(View.VISIBLE);
		}else if(Constants.status1.equals("have accepted")){
			holder.tv_status1.setVisibility(View.VISIBLE);
		}else if(Constants.status2.equals("have refused")){
			holder.tv_status2.setVisibility(View.VISIBLE);
		}
		*/
		 
		
		
	}catch(Exception e) {
		e.printStackTrace();
	}
		return view;
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
		TextView tv_status;

		public Holder(View view) {
			iv_item_userphoto = (ImageView) view.findViewById(R.id.img_item_userphoto);
			tv_item_uname = (TextView) view.findViewById(R.id.txt_item_uname);
			
			tv_item_friend = (TextView) view.findViewById(R.id.txt_item_friend);
			tv_item_time = (TextView) view.findViewById(R.id.txt_item_time);
			tv_item_content = (TextView) view.findViewById(R.id.txt_item_content);
			tv_item_logtime = (TextView) view.findViewById(R.id.txt_item_logtime);
			
			btn_item_accept = (Button) view.findViewById(R.id.btn_accept);
			btn_item_refuse = (Button) view.findViewById(R.id.btn_refuse);
			tv_status = (TextView) view.findViewById(R.id.txt_status);
		}
	}
}
