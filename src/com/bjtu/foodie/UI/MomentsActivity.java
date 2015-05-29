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
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.FullMomentItemAdapter;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.map.MapActivity;
import com.bjtu.foodie.utils.MomentTalkToServer;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MomentsActivity extends Activity {

	private FullMomentItemAdapter momentAdapter;
	private TextView tv_username;
	private ImageView iv_userPic;
	private ShareActionProvider myProvider;
	
	public static MomentsActivity instance;
	public static String replyIdString;
	
	int pageNo = 1;
	ArrayList<JSONObject> list = new ArrayList<JSONObject>();
	PullToRefreshListView mPullRefreshListView;
	private LinearLayout layoutNew;
	private EditText editTextNew;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moments);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		instance = this;
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())  
        .threadPriority(Thread.NORM_PRIORITY - 2)
        .denyCacheImageMultipleSizesInMemory()  
        .discCacheFileNameGenerator(new Md5FileNameGenerator())  
        .tasksProcessingOrder(QueueProcessingType.LIFO)  
        .build();  
    	ImageLoader.getInstance().init(config);
		
		tv_username = (TextView) findViewById(R.id.tv_username);
		iv_userPic = (ImageView) findViewById(R.id.iv_userPic);
		layoutNew = (LinearLayout) findViewById(R.id.layout_new);
		editTextNew = (EditText) findViewById(R.id.et_new_comment);
		
		momentAdapter = new FullMomentItemAdapter(this, list, layoutNew, editTextNew);

		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
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
				ListMomentTask task = new ListMomentTask();
				task.execute();
			}
		});
		
		mPullRefreshListView.requestLayout();
		mPullRefreshListView.setAdapter(momentAdapter);

		tv_username.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentMyMoment = new Intent(getApplicationContext(),
						MyMomentActivity.class);
				
				startActivity(intentMyMoment);
			}
		});
		
		findViewById(R.id.iv_bgimg).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Close",
					     Toast.LENGTH_SHORT).show();
				layoutNew.setVisibility(View.GONE);
				editTextNew.setText(null);
//				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			}
		});
		
		ListMomentTask task = new ListMomentTask();
		task.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.moments, menu);
		
		MenuItem shareItem = menu.findItem(R.id.action_share);
		myProvider = (ShareActionProvider) shareItem.getActionProvider();
		myProvider.setShareIntent(getDefaultIntent());
		
		return true;
	}

	private Intent getDefaultIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		return shareIntent;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_addMoments:
			Intent intentNewMoment = new Intent(this, MomentAddActivity1.class);
			startActivity(intentNewMoment);
			break;
		case R.id.action_map:
			Intent intentMap = new Intent(this, MapActivity.class);
			startActivity(intentMap);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class ListMomentTask extends AsyncTask<Object, Object, String>{
		ArrayList<JSONObject> tempList = new ArrayList<JSONObject>();
		String recipeRecult;
		
		@Override
		protected String doInBackground(Object... arg0) {
			recipeRecult = MomentTalkToServer.momentGet("moment/listAll?pageNo="+pageNo+"&pageSize=10");
			
			try {
				JSONObject jsonObject = new JSONObject(recipeRecult);
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
				Log.i(Constants.TAG_MOMENT, tempList.toString());
				list.addAll(tempList);
				mPullRefreshListView.setVisibility(View.GONE);  
				mPullRefreshListView.requestLayout();
				momentAdapter.notifyDataSetChanged();
				mPullRefreshListView.setVisibility(View.VISIBLE);
				
				mPullRefreshListView.onRefreshComplete();
				super.onPostExecute(result);
			}
		}
	}
	
	@Override
    protected void onResume() {  
        super.onResume();
        Log.v(Constants.TAG_MOMENT, "onResume");
    } 
}
