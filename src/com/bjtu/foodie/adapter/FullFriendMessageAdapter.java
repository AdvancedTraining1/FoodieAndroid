package com.bjtu.foodie.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.wifi.WifiConfiguration.Status;
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

import com.bjtu.foodie.R;
import com.bjtu.foodie.common.FoodieAndroidAPP;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.model.Friend;
import com.bjtu.foodie.model.FriendMessage;
import com.bjtu.foodie.model.User;
import com.bjtu.foodie.utils.FriendTalkToServer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class FullFriendMessageAdapter extends BaseAdapter{

	public List<FriendMessage> friendMessagesList;
	private FoodieAndroidAPP myApp; 
	FriendTalkToServer connect = new FriendTalkToServer();
	private Handler handler = new Handler();
	private Context context;
	DisplayImageOptions options;  
	final String urlString = "http://101.200.174.49:3000/";
	String dealmessage ="0";

	public UserDao userDao;
	
	public FullFriendMessageAdapter(List<FriendMessage> friendMessagesList, Context context) {
		super();
		
		this.friendMessagesList = friendMessagesList;
		this.context = context;
		userDao = new UserDao(context);
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return friendMessagesList==null  ? 0 : friendMessagesList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return friendMessagesList == null ? null : friendMessagesList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public String getFriendId(int position) {
		return friendMessagesList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		final Holder holder;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.friend_message_item,null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}
		

		final FriendMessage friendMessage = this.friendMessagesList.get(position);
		ImageLoader.getInstance()
		.displayImage(urlString+friendMessage.getFrom().getHead().trim(), holder.headImage, options, new SimpleImageLoadingListener() {
			
		});
				
		holder.account.setText(friendMessage.getFrom().getAccount());
		holder.id.setText(friendMessage.getFrom().getId());
		
		System.out.println("status---------"+friendMessage.getStatus());
		if (friendMessage.getStatus().equals("1")) {
			holder.agreebtn.setVisibility(View.GONE);
			holder.disAgreebtn.setVisibility(View.GONE);
			
			holder.statusMessage.setVisibility(View.VISIBLE);
			holder.statusMessage.setText("already add");
			
			
		}else if (friendMessage.getStatus().equals("2")) {
			holder.agreebtn.setVisibility(View.GONE);
			holder.disAgreebtn.setVisibility(View.GONE);
			
			holder.statusMessage.setVisibility(View.VISIBLE);
			holder.statusMessage.setText("already refuse");
			
		}else if (friendMessage.getStatus().equals("3")) {
			holder.messageTextView.setText(friendMessage.getFrom().getAccount() +" already refuse you");
			holder.agreebtn.setVisibility(View.GONE);
			holder.disAgreebtn.setVisibility(View.GONE);
			
		}else{
			holder.agreebtn.setVisibility(View.VISIBLE);
			holder.disAgreebtn.setVisibility(View.VISIBLE);
			holder.statusMessage.setVisibility(View.GONE);
			
			
			holder.agreebtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					dealmessage = "1";		
						
					String message = deal(dealmessage,friendMessage);
					
					if(message.equals("add successful")){
						
						holder.agreebtn.setVisibility(View.GONE);
						holder.disAgreebtn.setVisibility(View.GONE);
						
						holder.statusMessage.setVisibility(View.VISIBLE);
						holder.statusMessage.setText("already add");

					}
				}
			});
			
			holder.disAgreebtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					dealmessage = "2";		
					
					String message = deal(dealmessage,friendMessage);
					
					if(message.equals("refuse successful")){
						
						holder.agreebtn.setVisibility(View.GONE);
						holder.disAgreebtn.setVisibility(View.GONE);
						
						holder.statusMessage.setVisibility(View.VISIBLE);
						holder.statusMessage.setText("already refuse");

					}
					
				}
			});
			
			
		}
		
		
		return view;
	}
	
	
	private static class Holder {
		ImageView headImage = null;
	    TextView account = null;
	    TextView id = null;
	    TextView messageTextView = null;
	    Button agreebtn = null;
	    Button disAgreebtn = null;
	    TextView statusMessage = null;
	
		public Holder(View view) {
			headImage = (ImageView) view.findViewById(R.id.friend_head);
			account = (TextView) view.findViewById(R.id.friend_name);
			id = (TextView) view.findViewById(R.id.friend_id);
			messageTextView = (TextView) view.findViewById(R.id.message);
			agreebtn = (Button) view.findViewById(R.id.btn_agree_friend);
			disAgreebtn = (Button) view.findViewById(R.id.btn_disagree_friend);
			
			statusMessage = (TextView) view.findViewById(R.id.text_status);
    	
		}
	}
	
	public String deal(String status,FriendMessage friendMessage){
		
		String rmessage = "";
		
		User user = userDao.find();
		String tokenString = user.getToken();
		String urlString2 = "/service/friend/dealFriendMessage";

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("friendId", friendMessage.getFrom().getId()));
		postParameters.add(new BasicNameValuePair("friendAccount", friendMessage.getFrom().getAccount()));	
		postParameters.add(new BasicNameValuePair("friendHead", friendMessage.getFrom().getHead()));	
		postParameters.add(new BasicNameValuePair("messageId", friendMessage.getId()));			
		postParameters.add(new BasicNameValuePair("token", tokenString));
		postParameters.add(new BasicNameValuePair("status", status));
		
		try {
			
			String ListResult = connect.Post(urlString2, postParameters);			 
			JSONObject jsonObject = new JSONObject(ListResult);
			final String mymessage = jsonObject.getString("message");
			rmessage = mymessage;
			// 子线程不能修改main线程，所以必须用handler来对ui线程进行操作
			handler.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(context,mymessage, Toast.LENGTH_SHORT).show();
				}
			});
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("rmessage-----" + rmessage);
		
		return rmessage;
		
	}
	
	public void dataChange(List<FriendMessage> list){
		this.friendMessagesList = list;
		notifyDataSetChanged();
	}

}
