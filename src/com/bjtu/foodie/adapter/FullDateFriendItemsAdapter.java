package com.bjtu.foodie.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.User;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FullDateFriendItemsAdapter extends BaseAdapter {

	private List<User> friends;
	private Context context;
	ArrayList<Boolean> checkedItem = new ArrayList<Boolean>();
	ArrayList<String> idList = new ArrayList<String>();
	public ImageLoader imageLoader;
	
	
	public FullDateFriendItemsAdapter(Context contex, List<User> friends) {
		this.friends = friends;
		this.context = contex;
		for(int i=0;i<friends.size();i++){
            checkedItem.add(i,false);
            idList.add(i,null);
        }
	}
	
	public ArrayList<Boolean> getChecklist() {
		return checkedItem;
	}
	
	public ArrayList<String> getIDList() {
		return idList;
	}

	@Override
	public int getCount() {
		//Toast.makeText(context, "number" + dishes.size(), Toast.LENGTH_SHORT).show();
		return friends == null ? 0 : friends.size();
	}

	@Override
	public Object getItem(int arg0) {
		return friends == null ? null : friends.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	public String getFriendId(int arg0) {
		return friends.get(arg0).getId();
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		View view = arg1;
		Holder holder;
		final int p=position;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.friendslist,null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}

		User friend = this.friends.get(position);
		holder.name.setText(friend.getUsername());
		//holder.image.setImageResource(R.drawable.icon_avatar);
		
		/*ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())  
	    .threadPriority(Thread.NORM_PRIORITY - 2)
	    .denyCacheImageMultipleSizesInMemory()  
	    .discCacheFileNameGenerator(new Md5FileNameGenerator())  
	    .tasksProcessingOrder(QueueProcessingType.LIFO)  
	    .build();  
		ImageLoader.getInstance().init(config);*/
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
		
		
		ImageLoader.getInstance()
		.displayImage("http://192.168.1.103:3000/"+friends.get(position).getHead().trim(), holder.image, options, new SimpleImageLoadingListener() {
		});//101.200.174.49
		
		
		final String id = friend.getId();
		holder.checked.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
	                   //update the status of checkbox to checked
	                    
	                    checkedItem.set(p, true);
	                    idList.set(p,id);
	                }else{
	                  //update the status of checkbox to unchecked
	                    checkedItem.set(p, false);
	                    idList.set(p,null);
	                }
			}

        });
		return view;
	}
	

	private static class Holder {
		ImageView image = null;
	    TextView name = null;
	    CheckBox checked = null;

		public Holder(View view) {
			image = (ImageView) view.findViewById(R.id.img);
    		name = (TextView) view.findViewById(R.id.name);
    		checked = (CheckBox)view.findViewById(R.id.select);
		}
	}
}

