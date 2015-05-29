package com.bjtu.foodie.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.UI.LoginActivity;
import com.bjtu.foodie.UI.MomentSingleActivity;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.model.User;
import com.bjtu.foodie.utils.MomentTalkToServer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class FullMomentItemAdapter extends BaseAdapter {

	DisplayImageOptions options;
	private ArrayList<JSONObject> list;
	private Context context;
	public UserDao userDao;

	public FullMomentItemAdapter(Context context, ArrayList<JSONObject> list, LinearLayout newComment, EditText editTextNew) {
		this.context = context;
		this.list = list;
		userDao = new UserDao(context);
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(0))
		.build();
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	public JSONObject getItem(int positon){
		return list == null ? null : list.get(positon);
	}
	
	@Override
	public long getItemId(int position){
		return position;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		View view = arg1;
		Holder holder;
		if (null == view) {
			view = LayoutInflater.from(context).inflate(R.layout.moments_item, null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}
		
		JSONObject oneMoment = this.list.get(position);
		
		try {
			int likeNum = Integer.parseInt(oneMoment.getString("likeNum"));
			int commentNum = Integer.parseInt(oneMoment.getString("commentNum"));
			holder.tv_item_comment.setText(String.valueOf(commentNum+likeNum));
			holder.tv_item_uname.setText(oneMoment.getJSONObject("author").getString("account"));
			holder.tv_item_content.setText(oneMoment.getString("content"));
			holder.tv_item_time.setText(oneMoment.getString("date"));
			if(oneMoment.getString("location") == null || oneMoment.getString("location").equals("")){
				holder.tv_item_location.setVisibility(View.GONE);
			}else{
				holder.tv_item_location.setText(oneMoment.getString("location"));
			}
			ImageLoader.getInstance()
			.displayImage("http://101.200.174.49:3000/"+list.get(position).getJSONObject("author").getString("head").trim(), holder.iv_item_userphoto, options, new SimpleImageLoadingListener() {
			});
			ImageLoader.getInstance()
			.displayImage("http://101.200.174.49:3000/"+list.get(position).getString("picture").trim(), holder.iv_item_content_pic, options, new SimpleImageLoadingListener() {
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		holder.ib_item_comment.setOnClickListener(new OnClickCommentBtnListener(position));
//		holder.ib_item_like.setOnClickListener(new OnClickLikeBtnListener(position));
		return view;
	}
	
	private class OnClickCommentBtnListener implements OnClickListener {
		private int pos;
		
		public OnClickCommentBtnListener(int pos) {
			this.pos = pos;
		}
		@Override
		public void onClick(View v) {
			Log.i(Constants.TAG_MOMENT, "comment moment" + pos);
			Intent intent = new Intent(context, MomentSingleActivity.class);
			intent.putExtra(Constants.KEY_SINGLE_DATA, list.get(pos).toString());
			context.startActivity(intent);
		}
	}
	
	private class OnClickLikeBtnListener implements OnClickListener {
		private int pos;
		private String momentId;
		
		public OnClickLikeBtnListener(int pos) {
			this.pos = pos;
		}
		@Override
		public void onClick(View v) {
			Log.i(Constants.TAG_MOMENT, "like moment");
			try {
				momentId = list.get(pos).getString("_id");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			User user = userDao.find();
			if(user!=null){
				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair(Constants.POST_TOKEN, user.getToken()));
		        postParameters.add(new BasicNameValuePair(Constants.POST_MOMENT_ID, momentId));
		        
		        String resultString = MomentTalkToServer.momentPost("moment/addLike",postParameters);
		        if(resultString.equals("like moment success！")){
		        	Toast.makeText(context, "like success !",
						     Toast.LENGTH_SHORT).show();
		        }else{
		        	Toast.makeText(context, "already liked!",
						     Toast.LENGTH_SHORT).show();
		        }
	        }else {
				Intent loginIntent = new Intent(context,LoginActivity.class);
				context.startActivity(loginIntent);
			}
		}
	}

	private static class Holder {
		ImageView iv_item_userphoto;
		ImageView iv_item_content_pic;
		ImageButton ib_item_comment;
//		ImageButton ib_item_like;

//		TextView tv_item_like;
		TextView tv_item_comment;
		TextView tv_item_uname;
		TextView tv_item_time;
		TextView tv_item_content;
		TextView tv_item_location;
		
		public Holder(View view) {
//			tv_item_like = (TextView) view.findViewById(R.id.like_num);
			tv_item_comment = (TextView) view.findViewById(R.id.comment_num);
			tv_item_uname = (TextView) view.findViewById(R.id.txt_item_uname);
			tv_item_time = (TextView) view.findViewById(R.id.txt_item_time);
			tv_item_content = (TextView) view
					.findViewById(R.id.txt_item_content);
			tv_item_location = (TextView)view.findViewById(R.id.txt_item_location);
			
			iv_item_userphoto = (ImageView) view
					.findViewById(R.id.img_item_userphoto);
			iv_item_content_pic = (ImageView) view
					.findViewById(R.id.img_item_content_pic);
			ib_item_comment = (ImageButton) view.findViewById(R.id.imgBtn_comment);
//			ib_item_like = (ImageButton) view.findViewById(R.id.imgBtn_like);
			
		}
	}
}
