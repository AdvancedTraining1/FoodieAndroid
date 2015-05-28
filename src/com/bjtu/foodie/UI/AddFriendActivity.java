package com.bjtu.foodie.UI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.FullFriendItemSearchAdapter;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.model.Friend;
import com.bjtu.foodie.model.User;
import com.bjtu.foodie.utils.FriendTalkToServer;

public class AddFriendActivity extends Activity {

	FriendTalkToServer connect = new FriendTalkToServer();
	List<Friend> friendList = new ArrayList<Friend>();
	private Handler handler = new Handler();
	EditText searchContent;
	// SearchView searchView;
	Button btn_search;

	TextView noFoundHint;

	public UserDao userDao = new UserDao(this);

	ProgressDialog proDia;

	String searchContentS = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_friend);

		searchContent = (EditText) findViewById(R.id.search_content);
		// searchView = (SearchView) findViewById(R.id.searchView);
		btn_search = (Button) findViewById(R.id.searchView);
		noFoundHint = (TextView) findViewById(R.id.no_friend);
		
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				searchContentS = searchContent.getText().toString();

				if (TextUtils.isEmpty(searchContentS.trim())) {
					Toast.makeText(v.getContext(), "Please enter the username",
							Toast.LENGTH_LONG).show();
				} else {
					System.out.println("this run");
					proDia = ProgressDialog.show(AddFriendActivity.this,
							"Seach", "Seaching now,please wait!");
					proDia.show();

					friendList = getFriendList(searchContentS);
					if (friendList == null || friendList.size() == 0) {
						proDia.dismiss();
						noFoundHint.setVisibility(View.VISIBLE);

					} else {
						proDia.dismiss();

						Intent intent = new Intent();
						intent.setClass(AddFriendActivity.this,
								NewFriendSearchResultActivity.class);
						intent.putExtra("searchList", (Serializable) friendList); 
						startActivity(intent);
						

					}

				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_friend, menu);
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
	
	

	public List<Friend> getFriendList(String searchContent) {
		List<Friend> SearchList = new ArrayList<Friend>();


		List<Friend> myFriendList = new ArrayList<Friend>();
		User user = userDao.find();
		String tokenString = user.getToken();

		
		String myId = null;		
		String urlString="/service/friend/myId?token="+tokenString;
		try {
			String result = connect.testURLConn1(urlString);
			JSONObject jsonObject = new JSONObject(result);
			myId = jsonObject.getString("id");
			System.out.println("my id ----" +myId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	
		
		String friendListResult;		
		String urlString1="/service/friend/listFriend?token="+tokenString;
		try {
			friendListResult = connect.testURLConn1(urlString1);
			JSONObject jsonObject = new JSONObject(friendListResult);
			JSONArray jsonArray = jsonObject.getJSONArray("friendList");
			for(int i=0;i<jsonArray.length();i++){   
	            JSONObject jo = (JSONObject)jsonArray.opt(i);
	            String id = jo.getString("_id");
	            String account = jo.getString("account");
	            String head = jo.getString("head");
	            Friend friend = new Friend(id, account, head);
	            myFriendList.add(friend);
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		String ListResult;
		String urlString2 = "/service/friend/searchNewFriend";

		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("pageNo", "1"));
		postParameters.add(new BasicNameValuePair("pageSize", "10"));
		postParameters.add(new BasicNameValuePair("queryStr", searchContent));

		try {
			ListResult = connect.Post(urlString2, postParameters);
			JSONObject jsonObject = new JSONObject(ListResult);
			JSONArray jsonArray = jsonObject.getJSONArray("friendList");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = (JSONObject) jsonArray.opt(i);
				String id = jo.getString("_id");
				System.out.println(id);
				String account = jo.getString("account");
				String head = jo.getString("head");
				Friend friend = new Friend(id, account, head);
				
				boolean flag = false;
				if (id.equals(myId)) {
					flag = true;
				}
				
				for (Friend myfriend : myFriendList) {
					
					if(id.equals(myfriend.getId())){
						flag = true;
						break;
					}				
				}
				
				if (flag == false){

					System.out.println(friend.getId());
					SearchList.add(friend);
				}
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SearchList;
	}
}
