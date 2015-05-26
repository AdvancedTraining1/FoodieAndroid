package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.FullDateItemAdapter;
import com.bjtu.foodie.data.TestData;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.map.MapActivity;
import com.bjtu.foodie.model.DateModel;
import com.bjtu.foodie.model.Friend;
import com.bjtu.foodie.model.User;

public class DatesActivity extends Activity {
	
	ConnectToServer connect=new ConnectToServer();
	public UserDao userDao = new UserDao(this);
	
	// Listview adapter List<dates> List<comment>
	private ListView lv_allDates;
	private ArrayList<JSONObject> list_dates;
	private FullDateItemAdapter dateAdapter;

	private ShareActionProvider myProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dates);
		
		lv_allDates = (ListView) findViewById(R.id.lv_dates);
		
		//list_dates = (new TestData()).getDatesData();
		list_dates = getFriendDates();
		dateAdapter = new FullDateItemAdapter(this, list_dates);

		lv_allDates.setAdapter(dateAdapter);
/*
		tv_username.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentMyMoment = new Intent(getApplicationContext(),
						MyMomentActivity.class);
				
				startActivity(intentMyMoment);
			}
		});*/
		
	}

	public ArrayList<JSONObject> getFriendDates(){
		//List<DateModel> friendDates= new ArrayList<DateModel>();
		ArrayList<JSONObject> friendDates=new ArrayList<JSONObject>();
	 if(LoginActivity.token != null){
		
		User user = userDao.find();
		String tokenString = user.getToken();
		
		String friendDatesResult;		
		String urlString="/service/date/look?token="+tokenString;
		try {
			friendDatesResult = connect.testURLConn1(urlString);
			JSONObject jsonObject = new JSONObject(friendDatesResult);System.out.println("jsonObject=="+jsonObject);
			JSONArray jsonArray = jsonObject.getJSONArray("date");System.out.println("jsonArray=="+jsonArray);
			//friendCount = jsonObject.getInt("num");
			for(int i=0;i<jsonArray.length();i++){   
	            JSONObject jo = (JSONObject)jsonArray.opt(i);
	            /*String name = jo.getString("author.account");
	            String image = jo.getString("author.head");
	            String time = jo.getString("dateTime");
	            String address = jo.getString("dateContent");	            
	            String dateUsers = jo.getString("dateUsers");
	            
	            DateModel date = new DateModel(name, time, address);//????
*/	            friendDates.add(jo);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	 }else{
		 Intent loginIntent = new Intent(this, LoginActivity.class);
		 startActivity(loginIntent);
	 }
	 
		return friendDates;
		
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
			Intent intentNewDate = new Intent(this, AddDateActivity.class);
			startActivity(intentNewDate);
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
}
