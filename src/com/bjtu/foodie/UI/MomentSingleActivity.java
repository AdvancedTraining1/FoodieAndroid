package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.SingleMomentCommentAdapter;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.utils.MomentTalkToServer;
import com.bjtu.foodie.utils.Util;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MomentSingleActivity extends Activity{

	private ImageView userHeadImageView;
	private TextView userNameTextView;
	private TextView locationtTextView;
	private TextView dateTextView;
	private TextView contentTextView;
	private ImageView pictureImageView;
	private EditText commentEditText;
	
	public static MomentSingleActivity instance;
	private String intentMsg;
	private JSONObject singleMoment;
	private String momentId;
	
	int pageNo = 1;
	ArrayList<JSONObject> list = new ArrayList<JSONObject>();
	PullToRefreshListView mPullRefreshListView;
	DisplayImageOptions optionHead;
	DisplayImageOptions options;
	SingleMomentCommentAdapter momentAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moment_single);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		instance = this;
		
    	optionHead = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(90))
		.build();
    	
    	options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(0))
		.build();
		
		Intent intent = getIntent();
		intentMsg = intent.getStringExtra(Constants.KEY_SINGLE_DATA);
		try {
			singleMoment = new JSONObject(intentMsg);
			momentId = singleMoment.getString("_id");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.i(Constants.TAG_MOMENT, "single moment data:" + singleMoment);
		
//		initView();
		commentEditText = (EditText) findViewById(R.id.ed_comment);
		momentAdapter = new SingleMomentCommentAdapter(this, list, commentEditText);

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list_comment);
		mPullRefreshListView.setMode(Mode.PULL_UP_TO_REFRESH);
		mPullRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel(
				"pull to load");
		mPullRefreshListView.getLoadingLayoutProxy(false, true)
				.setRefreshingLabel("loading");
		mPullRefreshListView.getLoadingLayoutProxy(false, true)
				.setReleaseLabel("release to load");
		
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {	
			}
			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				String label = DateUtils.formatDateTime(
						getApplicationContext(),
						System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				pageNo += 1;
				ListCommentTask task = new ListCommentTask();
				task.execute();
			}
		});
		
		mPullRefreshListView.requestLayout();
		mPullRefreshListView.setAdapter(momentAdapter);
		
		ListCommentTask task = new ListCommentTask();
		task.execute();
	}

	private void initView(){
		commentEditText = (EditText) findViewById(R.id.ed_comment);
		userHeadImageView = (ImageView)findViewById(R.id.img_item_userphoto);
		userNameTextView  = (TextView) findViewById(R.id.txt_item_uname);
		contentTextView  = (TextView) findViewById(R.id.txt_item_content);
		pictureImageView = (ImageView)findViewById(R.id.img_item_content_pic);
		pictureImageView.setMinimumWidth(Util.getScreenWidthDp(this));
		pictureImageView.setMinimumHeight(Util.getScreenWidthDp(this)/2);
		
		try {
			ImageLoader.getInstance()
			.displayImage("http://101.200.174.49:3000/"+singleMoment.getJSONObject("author").getString("head").trim(), userHeadImageView, optionHead, new SimpleImageLoadingListener() {
			});
			ImageLoader.getInstance()
			.displayImage("http://101.200.174.49:3000/"+singleMoment.getString("picture").trim(), pictureImageView, options, new SimpleImageLoadingListener() {
			});
			userNameTextView.setText(singleMoment.getJSONObject("author").getString("account"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void addComment(View view){
		Log.i(Constants.TAG_MOMENT,momentId+";"+commentEditText.getText().toString()+";");
		if(commentEditText.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "Enter comment please",
				     Toast.LENGTH_SHORT).show();
		}
		if(LoginActivity.token != null){
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair(Constants.POST_TOKEN, LoginActivity.token));
	        postParameters.add(new BasicNameValuePair(Constants.POST_CONTENT, commentEditText.getText().toString()));
	        postParameters.add(new BasicNameValuePair(Constants.POST_MOMENT_ID, momentId));
	        
	        String resultString = MomentTalkToServer.momentPost("moment/addComment",postParameters);
	        if(resultString.equals("comment success！")){
	        	Toast.makeText(getApplicationContext(), "comment success !",
					     Toast.LENGTH_SHORT).show();
	        	
	        }
        }else {
			Intent loginIntent = new Intent(this,LoginActivity.class);
			startActivity(loginIntent);
		}
	}
	
	class ListCommentTask extends AsyncTask<Object, Object, String>{
		ArrayList<JSONObject> tempList = new ArrayList<JSONObject>();
		String commentRecult;
		
		@Override
		protected String doInBackground(Object... arg0) {
			commentRecult = MomentTalkToServer.momentGet("moment/getCommentById?id="+momentId+"&pageNo="+pageNo+"&pageSize=10");
			
			try {
				JSONObject jsonObject = new JSONObject(commentRecult);
				JSONArray jsonArray = jsonObject.getJSONArray("root");
				if(jsonArray.length() == 0){
					return "No";
				}
				for(int i=0;i<jsonArray.length();i++){   
	                JSONObject jo = (JSONObject)jsonArray.opt(i);
	                tempList.add(jo);
	            }
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		public void onPostExecute(String result){
			if(result != null && result.equals("No")){
				Toast.makeText(getApplicationContext(), "No more",
					     Toast.LENGTH_SHORT).show();
				mPullRefreshListView.onRefreshComplete();
				super.onPostExecute(null);
			}else{
				list.addAll(tempList);
//				Log.i(Constants.TAG_MOMENT, list.toString());
				mPullRefreshListView.setVisibility(View.GONE);  
				mPullRefreshListView.requestLayout();
				momentAdapter.notifyDataSetChanged();
				mPullRefreshListView.setVisibility(View.VISIBLE);
				
				mPullRefreshListView.onRefreshComplete();
				super.onPostExecute(result);
			}
		}
	}
	
}
