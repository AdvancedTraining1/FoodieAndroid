package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.SimpleMomentItemAdapter;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.utils.MomentTalkToServer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class MyMomentActivity extends Activity {

	ListView lv_myMoments;
	List<JSONObject> myMomentList = new ArrayList<JSONObject>();
	SimpleMomentItemAdapter myMomentAdapter;
	ImageButton imgbtn_newMoment;
	String userId;
	String userAccount;
	TextView tv_username;
	TextView tv_username_black;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_moments);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		userId = intent.getStringExtra(Constants.KEY_USER_ID);
		userAccount = intent.getStringExtra(Constants.KEY_USER_ACCOUNT);
		
		tv_username = (TextView) findViewById(R.id.tv_username);
		tv_username_black = (TextView) findViewById(R.id.tv_username_black);
		if(userAccount != null){
			tv_username.setText(userAccount);
			tv_username_black.setText(userAccount);
		}
			tv_username.setText(userAccount);
		
		lv_myMoments = (ListView) findViewById(R.id.lv_myMoments);
		imgbtn_newMoment = (ImageButton) findViewById(R.id.iv_photo);
		
		myMomentAdapter = new SimpleMomentItemAdapter(this, myMomentList);
		lv_myMoments.setAdapter(myMomentAdapter);
		
		lv_myMoments.setOnItemClickListener(new OnItemClickListener() {  
	        @Override  
	        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
	        	Intent intent = new Intent(MyMomentActivity.this, MomentSingleActivity.class);
	        	intent.putExtra(Constants.KEY_SINGLE_DATA, myMomentList.get(position).toString());
				startActivity(intent);
	        }  
	    });
		
		ListMomentTask momentTask = new ListMomentTask();
		momentTask.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_moment, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	class ListMomentTask extends AsyncTask<Object, Object, String>{
		ArrayList<JSONObject> tempList = new ArrayList<JSONObject>();
		String momentRecult;
		
		@Override
		protected String doInBackground(Object... arg0) {
			momentRecult = MomentTalkToServer.momentGet("moment/listOwn?authorId="+userId);
			
			try {
				JSONObject jsonObject = new JSONObject(momentRecult);
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
			myMomentList.addAll(tempList);
			myMomentAdapter.notifyDataSetChanged();
			super.onPostExecute(null);
		}
	}
	
	public String getUserAccount(String userId){
		return null;
	}
}
