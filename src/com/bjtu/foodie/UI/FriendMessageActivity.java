package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.FullFriendMessageAdapter;
import com.bjtu.foodie.model.FriendMessage;

public class FriendMessageActivity extends Activity {
	
	List<FriendMessage> friendMessageList = new ArrayList<FriendMessage>();
	ListView myfriendMessageList;
	TextView noFriendMessageHintTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_message);
		
		myfriendMessageList = (ListView) findViewById(R.id.friend_message_listview);
		noFriendMessageHintTextView = (TextView) findViewById(R.id.no_message_hint);
		
		
		friendMessageList = (List<FriendMessage>) getIntent().getSerializableExtra("friendMessageList");
		
		System.out.println("FriendMessageActivity---" + friendMessageList.size());
		
		if (friendMessageList.size() == 0) {
			
			noFriendMessageHintTextView.setVisibility(View.VISIBLE);
		}else {
			FullFriendMessageAdapter adapter = new FullFriendMessageAdapter(friendMessageList,this);
			myfriendMessageList.setAdapter(adapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend_message, menu);
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
}
