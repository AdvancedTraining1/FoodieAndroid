package com.bjtu.foodie.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import com.bjtu.foodie.UI.MomentsActivity;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.utils.MomentTalkToServer;
import com.bjtu.foodie.utils.TitlePopup;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class SingleMomentCommentAdapter extends BaseAdapter {

	DisplayImageOptions options;
	private ArrayList<JSONObject> list;
	private Context context;
	private EditText commentEditText;

	public SingleMomentCommentAdapter(Context context, ArrayList<JSONObject> list, EditText commentEditText) {
		this.context = context;
		this.list = list;
		this.commentEditText = commentEditText;
		
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(90))
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
			view = LayoutInflater.from(context).inflate(R.layout.moment_comment_item, null);
			holder = new Holder(view);
			view.setTag(holder);

		} else {
			holder = (Holder) view.getTag();
		}
		JSONObject oneComment = this.list.get(position);
		
		try {
			holder.accountTextView.setText(oneComment.getJSONObject("author").getString("account"));
			holder.timetTextView.setText(oneComment.getString("date"));
			holder.contentTextView.setText(oneComment.getString("content"));
			ImageLoader.getInstance()
			.displayImage("http://101.200.174.49:3000/"+oneComment.getJSONObject("author").getString("head").trim(), holder.headImageView, options, new SimpleImageLoadingListener() {
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
		holder.headImageView.setOnClickListener(new OnClickUserListener(position));
		holder.commentLayout.setOnClickListener(new OnClickCommentListener(position));
		return view;
	}
	
	private class OnClickCommentListener implements OnClickListener {
		private int pos;
		
		public OnClickCommentListener(int pos) {
			this.pos = pos;
		}
		@Override
		public void onClick(View v) {
			Log.i(Constants.TAG_MOMENT, "comment moment" + pos);
			try {
				String replyString = "reply "+list.get(pos).getJSONObject("author").getString("account")+": ";
				commentEditText.setText(replyString);
				commentEditText.setSelection(replyString.length());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class OnClickUserListener implements OnClickListener {
		private int pos;
		private String momentId;
		
		public OnClickUserListener(int pos) {
			this.pos = pos;
		}
		@Override
		public void onClick(View v) {
			Log.i(Constants.TAG_MOMENT, "click"+pos);
			/*try {
				momentId = list.get(pos).getString("_id");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			if(LoginActivity.token != null){
				List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair(Constants.POST_TOKEN, LoginActivity.token));
		        postParameters.add(new BasicNameValuePair(Constants.POST_MOMENT_ID, momentId));
		        
		        String resultString = MomentTalkToServer.momentPost("moment/addLike",postParameters);
		        if(resultString.equals("like moment success！")){
		        	Toast.makeText(context, "like success !",
						     Toast.LENGTH_SHORT).show();
		        }
	        }else {
				Intent loginIntent = new Intent(context,LoginActivity.class);
				context.startActivity(loginIntent);
			}*/
		}
	}

	private static class Holder {
		LinearLayout commentLayout;
		ImageView headImageView;

		TextView accountTextView;
		TextView timetTextView;
		TextView contentTextView;
		
		public Holder(View view) {
			commentLayout = (LinearLayout) view.findViewById(R.id.comment_linear);
			accountTextView = (TextView) view.findViewById(R.id.tv_comment_name);
			timetTextView = (TextView) view.findViewById(R.id.tv_comment_time);
			contentTextView = (TextView) view.findViewById(R.id.tv_comment_content);
			
			headImageView = (ImageView) view.findViewById(R.id.comment_head);
			
		}
	}
}
