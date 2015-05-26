package com.bjtu.foodie.adapter;

import java.util.List;

import org.json.JSONException;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjtu.foodie.R;
import com.bjtu.foodie.UI.FriendListActivity;
import com.bjtu.foodie.common.FoodieAndroidAPP;
import com.bjtu.foodie.model.Friend;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class FullFriendItemAdapter extends BaseAdapter{
	
	private List<Friend> friendList;
	private FoodieAndroidAPP myApp; 
	private Context context;
	DisplayImageOptions options;  
//	FriendListActivity context;
	final String urlString = "http://192.168.1.103:3000/";
	
	public FullFriendItemAdapter(List<Friend> friendList, Context context) {
		super();
		this.friendList = friendList;
		this.context = context;
		
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
//	public FullFriendItemAdapter(List<Friend> friendList, FriendListActivity context) {
//		super();
//		this.friendList = friendList;
//		this.context = context;
//	}

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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Holder holder;
		final int p=position;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.friendlist_item,null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}
		

		Friend friend = this.friendList.get(position);
	//	holder.headImage.setImageResource(friend.getHead());
		ImageLoader.getInstance()
		.displayImage(urlString+friend.getHead().trim(), holder.headImage, options, new SimpleImageLoadingListener() {
			
		});
				
		holder.account.setText(friend.getAccount());
		holder.id.setText(friend.getId());
		
		//---------getPic == id of the pic in drawable-------
//		holder.iamge.setImageResource(dish.getPic());
		//final String id = friend.getId();
		
		return view;
	}
	
	private static class Holder {
		ImageView headImage = null;
	    TextView account = null;
	    TextView id = null;
	
		public Holder(View view) {
			headImage = (ImageView) view.findViewById(R.id.friend_head);
			account = (TextView) view.findViewById(R.id.friend_name);
			id = (TextView) view.findViewById(R.id.friend_id);
    	
		}
	}

}
