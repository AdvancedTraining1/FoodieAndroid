package com.bjtu.foodie.adapter;

import java.util.ArrayList;
import org.json.JSONObject;

import com.bjtu.foodie.R;
import com.nostra13.universalimageloader.core.ImageLoader;
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

	//private List<User> friends;
	private ArrayList<JSONObject> friends;
	private Context context;
	ArrayList<Boolean> checkedItem = new ArrayList<Boolean>();
	ArrayList<String> idList = new ArrayList<String>();
	public ImageLoader imageLoader;
	ArrayList<Object> item = new ArrayList<Object>();
	ArrayList<String> nameList = new ArrayList<String>();
	
	
	public FullDateFriendItemsAdapter(Context contex, ArrayList<JSONObject> friends) {
		this.friends = friends;
		this.context = contex;
		for(int i=0;i<friends.size();i++){
            checkedItem.add(i,false);
            idList.add(i,null);
            item.add(i,null);
            nameList.add(i,null);
        }
	}
	
	public ArrayList<Boolean> getChecklist() {
		return checkedItem;
	}
	
	public ArrayList<String> getIDList() {
		return idList;
	}
	
	public ArrayList<String> getNameList() {
		return nameList;
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

	/*public String getFriendId(int arg0) {
		return friends.get(arg0).getId();
	}*/
	/*同样先将json字符串转换为json对象，再将json对象转换为java对象，如下所示。
	JSONObject obj = new JSONObject().fromObject(jsonStr);//将json字符串转换为json对象
	将json对象转换为java对象
	Person jb = (Person)JSONObject.toBean(obj,Person.class);//将建json对象转换为Person对象
*/	

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

	 try{
		final JSONObject friend = this.friends.get(position);
		holder.name.setText(friend.getString("account"));
		holder.image.setImageResource(R.drawable.jay);
		//动态显示头像2.img
		/*ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context.getApplicationContext())  
	    .threadPriority(Thread.NORM_PRIORITY - 2)
	    .denyCacheImageMultipleSizesInMemory()  
	    .discCacheFileNameGenerator(new Md5FileNameGenerator())  
	    .tasksProcessingOrder(QueueProcessingType.LIFO)  
	    .build();  
		ImageLoader.getInstance().init(config);
		
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
		.displayImage("http://219.242.243.52:3000/"+friend.getString("head").trim(), holder.image, options, new SimpleImageLoadingListener() {
		});*/
		//101.200.174.49 server
		//192.168.1.103 wifi
		final String name = friend.getString("account");
		final String id = friend.getString("_id");
		holder.checked.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
	                   //update the status of checkbox to checked
	                    
	                    checkedItem.set(p, true);
	                    idList.set(p,id);
	                    item.set(p, friend);
	                    nameList.set(p, name);
	                }else{
	                  //update the status of checkbox to unchecked
	                    checkedItem.set(p, false);
	                    idList.set(p,null);
	                    item.set(p, null);
	                    nameList.set(p, null);
	                }
			}

        });
		
	 }catch(Exception e) {
		e.printStackTrace();
	 }
	 
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

