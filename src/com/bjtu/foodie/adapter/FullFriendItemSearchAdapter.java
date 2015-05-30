package com.bjtu.foodie.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
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
import com.bjtu.foodie.UI.LoginActivity;
import com.bjtu.foodie.UI.MainActivity;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.common.FoodieAndroidAPP;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.model.Friend;
import com.bjtu.foodie.model.User;
import com.bjtu.foodie.utils.FriendTalkToServer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class FullFriendItemSearchAdapter extends BaseAdapter{
	
	final String urlString = "http://101.200.174.49:3000/";
	
	public List<Friend> friendList;
	private FoodieAndroidAPP myApp; 
	FriendTalkToServer connect = new FriendTalkToServer();
	private Handler handler = new Handler();
	private Context context;
	DisplayImageOptions options;  

	public UserDao userDao;
	
	public FullFriendItemSearchAdapter(List<Friend> friendList, Context context) {
		super();
		
		this.friendList = friendList;
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
		return friendList==null  ? 0 : friendList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return friendList == null ? null : friendList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public String getFriendId(int position) {
		return friendList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		final Holder holder;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.friend_item_search,null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}
		

		final Friend friend = this.friendList.get(position);
		ImageLoader.getInstance()
		.displayImage(urlString+friend.getHead().trim(), holder.headImage, options, new SimpleImageLoadingListener() {
			
		});
				
		holder.account.setText(friend.getAccount());
		holder.id.setText(friend.getId());
		
		holder.addbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
					
					User user = userDao.find();
					String tokenString = user.getToken();
					final String message;
					String urlString2 = "/service/friend/saveFriendMessage";

					List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("friendId", friend.getId()));
					postParameters.add(new BasicNameValuePair("friendAccount", friend.getAccount()));			
					postParameters.add(new BasicNameValuePair("friendHead", friend.getHead()));			
					postParameters.add(new BasicNameValuePair("token", tokenString));
					String ListResult = connect.Post(urlString2, postParameters);
					JSONObject jsonObject = new JSONObject(ListResult);
					message = jsonObject.getString("message");
					// 子线程不能修改main线程，所以必须用handler来对ui线程进行操作
					handler.post(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(context,message, Toast.LENGTH_SHORT).show();
						}
					});
					
					if(message.equals("send successful")){
						
						System.out.println("yes this ...");
						holder.addbtn.setText("等待验证");
						holder.addbtn.setEnabled(false);
						//notifyDataSetChanged();

					}
					
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		return view;
	}
	
	
	private static class Holder {
		ImageView headImage = null;
	    TextView account = null;
	    TextView id = null;
	    Button addbtn = null;
	
		public Holder(View view) {
			headImage = (ImageView) view.findViewById(R.id.friend_head);
			account = (TextView) view.findViewById(R.id.friend_name);
			id = (TextView) view.findViewById(R.id.friend_id);
			addbtn = (Button) view.findViewById(R.id.btn_add_friend);
    	
		}
	}
	
	
	public void dataChange(List<Friend> list){
		this.friendList = list;
		notifyDataSetChanged();
	}

	
	

}
