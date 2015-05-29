package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.SimpleMomentItemAdapter;
import com.bjtu.foodie.utils.MomentTalkToServer;

public class MyMomentActivity extends Activity {

	ListView lv_myMoments;
	List<JSONObject> myMomentList = new ArrayList<JSONObject>();
	SimpleMomentItemAdapter myMomentAdapter;
	ImageButton imgbtn_newMoment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_moments);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		lv_myMoments = (ListView) findViewById(R.id.lv_myMoments);
		imgbtn_newMoment = (ImageButton) findViewById(R.id.iv_photo);
		
		myMomentAdapter = new SimpleMomentItemAdapter(this, myMomentList);
		lv_myMoments.setAdapter(myMomentAdapter);
		
		imgbtn_newMoment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		ListMomentTask momentTask = new ListMomentTask();
		momentTask.execute();
		
		setListViewHeight(lv_myMoments);
		//ListView listview = (ListView)findViewById(R.id.lv_myMoments);
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
			momentRecult = MomentTalkToServer.momentGet("moment/listOwn?authorId=555055e95f51f5be307902ad");
			
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
	
	public void setListViewHeight(ListView listView) {
		// ListView listview = (ListView) findViewById(R.id.lv_myMoments);
		int totalHeight = 0;
		SimpleMomentItemAdapter adapter = (SimpleMomentItemAdapter) listView
				.getAdapter();
		if (null != adapter) {
			for (int i = 0; i < adapter.getCount(); i++) {
				View listItem = adapter.getView(i, null, listView);
				if (null != listItem) {
					listItem.measure(0, 0);// 注意listview子项必须为LinearLayout才能调用该方法
					totalHeight += listItem.getMeasuredHeight();
				}
			}

			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight
					+ (listView.getDividerHeight() * (listView.getCount() - 1));
			listView.setLayoutParams(params);
		}
	}
}
