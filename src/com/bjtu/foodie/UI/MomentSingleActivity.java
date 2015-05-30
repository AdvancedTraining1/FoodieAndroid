package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.SimpleMomentLikeAdapter;
import com.bjtu.foodie.adapter.SingleMomentCommentAdapter;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.model.User;
import com.bjtu.foodie.utils.MomentTalkToServer;
import com.bjtu.foodie.utils.Util;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MomentSingleActivity extends Activity implements OnItemClickListener{

	private ImageView userHeadImageView;
	private TextView userNameTextView;
	private TextView contentTextView;
	private ImageView pictureImageView;
	private EditText commentEditText;
	private ListView commentListView;
	private GridView likeGridView;
	private LinearLayout likeListView;
	private ImageButton unlikeButton;
	private ImageButton likedButton;
	
	public static MomentSingleActivity instance;
	private String intentMsg;
	private JSONObject singleMoment;
	private String momentId;
	
	int pageNo = 1;
	ArrayList<JSONObject> list = new ArrayList<JSONObject>();
	ArrayList<JSONObject> likeList = new ArrayList<JSONObject>();
	DisplayImageOptions optionHead;
	DisplayImageOptions options;
	SingleMomentCommentAdapter momentAdapter;
	SimpleMomentLikeAdapter likeAdapter;
	
	public UserDao userDao = new UserDao(this);
	private String userId;
	
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
		
		initView();
		
		momentAdapter = new SingleMomentCommentAdapter(this, list, commentEditText);
		commentListView.setAdapter(momentAdapter);
		
		ListCommentTask task = new ListCommentTask();
		task.execute();
	}

	private void initView(){
		commentListView = (ListView) findViewById(R.id.pull_refresh_list_comment);
		commentEditText = (EditText) findViewById(R.id.ed_comment);
		userHeadImageView = (ImageView)findViewById(R.id.img_item_userphoto);
		userNameTextView  = (TextView) findViewById(R.id.txt_item_uname);
		contentTextView  = (TextView) findViewById(R.id.txt_item_content);
		pictureImageView = (ImageView)findViewById(R.id.img_item_content_pic);
		pictureImageView.setMinimumWidth(Util.getScreenWidthDp(this));
		pictureImageView.setMinimumHeight(Util.getScreenWidthDp(this));
		unlikeButton = (ImageButton)findViewById(R.id.ib_like_moment);
		likedButton = (ImageButton)findViewById(R.id.ib_concel_like);
		likeListView = (LinearLayout)findViewById(R.id.linear_like);
		
		/*commentEditText.clearFocus(); 
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
        imm.hideSoftInputFromWindow(commentEditText.getWindowToken(),0); */
		User user = userDao.find();
		userId = user.getId();
		
		try {
			ImageLoader.getInstance()
			.displayImage("http://101.200.174.49:3000/"+singleMoment.getJSONObject("author").getString("head").trim(), userHeadImageView, options, new SimpleImageLoadingListener() {
			});
			ImageLoader.getInstance()
			.displayImage("http://101.200.174.49:3000/"+singleMoment.getString("picture").trim(), pictureImageView, options, new SimpleImageLoadingListener() {
			});
			userNameTextView.setText(singleMoment.getJSONObject("author").getString("account"));
			JSONArray likeArray = singleMoment.getJSONArray("likeList");
			if(likeArray == null || likeArray.length() == 0){
				unlikeButton.setVisibility(View.VISIBLE);
			}else{
				likeListView.setVisibility(View.VISIBLE);
				for(int i = 0 ; i < likeArray.length() ; i++){
							
					if(((JSONObject)likeArray.get(i)).getString("_id").equals(userId)){
						likedButton.setVisibility(View.VISIBLE);
						Log.i(Constants.TAG_MOMENT, "liked");
					}
					likeList.add((JSONObject) likeArray.get(i));
					
					ImageView imageView = new ImageView(this);
					LayoutParams params = new LayoutParams(50, 50);  
					params.height = 110;
					params.width = 120;
					imageView.setLayoutParams(params);
					imageView.setPadding(10, 0, 10, 10);
					ImageLoader.getInstance()
					.displayImage("http://101.200.174.49:3000/"+((JSONObject)likeArray.get(i)).getString("head").trim(), imageView, optionHead, new SimpleImageLoadingListener() {
					});
					imageView.setScaleType(ScaleType.CENTER);
					likeListView.addView(imageView);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		unlikeButton.setOnClickListener(new Button.OnClickListener(){  
            public void onClick(View v) {  
            	Log.i(Constants.TAG_MOMENT, "like moment");
        		
        		User user = userDao.find();
        		if(user!=null){
        			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        			postParameters.add(new BasicNameValuePair(Constants.POST_TOKEN, user.getToken()));
        	        postParameters.add(new BasicNameValuePair(Constants.POST_MOMENT_ID, momentId));
        	        
        	        String resultString = MomentTalkToServer.momentPost("moment/addLike",postParameters);
        	        if(resultString.equals("like moment success！")){
        	        	Toast.makeText(MomentSingleActivity.this, "like success !",
        					     Toast.LENGTH_SHORT).show();
        	        	
        	        	likeListView.setVisibility(View.VISIBLE);
        	        	ImageView imageView = new ImageView(MomentSingleActivity.this);
        				LayoutParams params = new LayoutParams(50, 50);  
        				params.height = 110;
        				params.width = 120;
        				imageView.setLayoutParams(params);
        				imageView.setPadding(10, 0, 10, 10);
        				ImageLoader.getInstance()
        				.displayImage("http://101.200.174.49:3000/head/defaulthead.jpeg", imageView, optionHead, new SimpleImageLoadingListener() {
        				});
        				imageView.setScaleType(ScaleType.CENTER);
        				likeListView.addView(imageView);
        				
        				unlikeButton.setVisibility(View.INVISIBLE);
        				likedButton.setVisibility(View.VISIBLE);
        	        }
                }else {
        			Intent loginIntent = new Intent(MomentSingleActivity.this,LoginActivity.class);
        			startActivity(loginIntent);
        		}
            }  
        });
		
		likedButton.setOnClickListener(new Button.OnClickListener(){  
            public void onClick(View v) {
            	Toast.makeText(MomentSingleActivity.this, "already liked!",
					     Toast.LENGTH_SHORT).show();
            }  
        });
	}
	
	public void addComment(View view){
		Log.i(Constants.TAG_MOMENT,momentId+";"+commentEditText.getText().toString()+";");
		if(commentEditText.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "Enter comment please",
				     Toast.LENGTH_SHORT).show();
		}
		User user = userDao.find();
		if(user!=null){
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair(Constants.POST_TOKEN, user.getToken()));
	        postParameters.add(new BasicNameValuePair(Constants.POST_CONTENT, commentEditText.getText().toString()));
	        postParameters.add(new BasicNameValuePair(Constants.POST_MOMENT_ID, momentId));
	        
	        String resultString = MomentTalkToServer.momentPost("moment/addComment",postParameters);
	        if(resultString.equals("comment success！")){
	        	Toast.makeText(getApplicationContext(), "comment success !",
					     Toast.LENGTH_SHORT).show();
	        	commentEditText.setText(null);
	        	commentEditText.setFocusableInTouchMode(false);
	        	commentEditText.clearFocus();
	        	RefreshCommentTask refreshCommentTask = new RefreshCommentTask();
	        	refreshCommentTask.execute();
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
			commentRecult = MomentTalkToServer.momentGet("moment/getCommentById?id="+momentId+"&pageNo="+pageNo+"&pageSize=0");
			
			try {
				JSONObject jsonObject = new JSONObject(commentRecult);
				JSONArray jsonArray = jsonObject.getJSONArray("root");
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
			list.addAll(tempList);
			Log.i(Constants.TAG_MOMENT, list.toString());
			momentAdapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}
	}
	
	
	class RefreshCommentTask extends AsyncTask<Object, Object, Object>{
		ArrayList<JSONObject> taskList = new ArrayList<JSONObject>();
		
		@Override
		protected Object doInBackground(Object... arg0) {
			//评论信息
			String commentRecult = MomentTalkToServer.momentGet("moment/getCommentById?id="+momentId+"&pageNo="+pageNo+"&pageSize=0");
			try {
				JSONObject jsonObject = new JSONObject(commentRecult);
				JSONArray jsonArray = jsonObject.getJSONArray("root");
				for(int i=0;i<jsonArray.length();i++){   
	                JSONObject jo = (JSONObject)jsonArray.opt(i);
	                taskList.add(jo);
	            }
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		public void onPostExecute(Object result){
			list.clear();
			list.addAll(taskList);
			momentAdapter.notifyDataSetChanged();
//			showComment();
		}
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
		System.out.println("-----pos----"+pos);
		Log.i(Constants.TAG_MOMENT, "v"+pos);
		/*Intent intent = new Intent(this, SingleRecipeActivity.class);
		
		try {
			intent.putExtra(Constants.INTENT_EXTRA_SINGLE_RECIPE_ID, list.get(pos-1).getString("_id"));
			intent.putExtra(Constants.INTENT_EXTRA_SINGLE_RECIPE, list.get(pos-1).toString());
			startActivity(intent);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public void toSingleUser(View view){
		Intent intentMyMoment = new Intent(getApplicationContext(),
				MyMomentActivity.class);
		intentMyMoment.putExtra(Constants.KEY_USER_ID, userId);
		startActivity(intentMyMoment);
	}
}
