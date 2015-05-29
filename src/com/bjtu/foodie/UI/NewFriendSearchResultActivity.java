package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.FullFriendItemSearchAdapter;
import com.bjtu.foodie.model.Friend;

public class NewFriendSearchResultActivity extends Activity {
	
	List<Friend> friendList = new ArrayList<Friend>();
	ListView mySearchList;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_friend_search_result);
		
		mySearchList = (ListView) findViewById(R.id.search_result_list);
		
		friendList = (List<Friend>) getIntent().getSerializableExtra("searchList");
		
		System.out.println("NewFriendSearchResultActivity---" + friendList.size());
//		 m_DishData = (new TestDishes()).getData();
//	     
//		FullDishItemsAdapter2 m_adapter2 = new FullDishItemsAdapter2(this, m_DishData);
//		mySearchList.setAdapter(m_adapter2);
        
		FullFriendItemSearchAdapter adapter = new FullFriendItemSearchAdapter(friendList,this);
		mySearchList.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_friend_search_result, menu);
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
	
	public List<Friend> getFriendList(String searchResult) {
		List<Friend> SearchList = new ArrayList<Friend>();
		try {
		
			JSONObject jsonObject = new JSONObject(searchResult);
			System.out.println("list result-----" + searchResult);
			JSONArray jsonArray = jsonObject.getJSONArray("friendList");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = (JSONObject) jsonArray.opt(i);
				String id = jo.getString("_id");
				System.out.println(id);
				String account = jo.getString("account");
				String head = jo.getString("head");
				Friend friend = new Friend(id, account, head);
				System.out.println(friend.getId());
				SearchList.add(friend);
			}

			System.out.println("SearchList---" + SearchList.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SearchList;
	}
}
