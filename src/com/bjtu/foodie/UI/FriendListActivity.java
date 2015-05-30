package com.bjtu.foodie.UI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.FullFriendItemAdapter;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.model.Friend;
import com.bjtu.foodie.model.FriendMessage;
import com.bjtu.foodie.model.User;


public class FriendListActivity extends Activity {
	ConnectToServer connect=new ConnectToServer();	
	ListView friendListView;
	List<Friend> friendList =new ArrayList<Friend>();
	List<FriendMessage> friendMessageList = new ArrayList<FriendMessage>();
	TextView noFriend;
	Button btn_friendMessage;
	public UserDao userDao = new UserDao(this);
	
	public static int friendCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_list);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		friendListView = (ListView) findViewById(R.id.friend_list);
		noFriend = (TextView) findViewById(R.id.no_friend);
		btn_friendMessage = (Button) findViewById(R.id.btn_friendMessage);
		btn_friendMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				friendMessageList = getMessgaeList();
				FriendListActivity.this.finish();
				Intent intent = new Intent();
				intent.setClass(FriendListActivity.this,
						FriendMessageActivity.class);
				intent.putExtra("friendMessageList", (Serializable) friendMessageList); 
				startActivity(intent);
				
				
			}
		});
		
		friendList = getFriendList();
		if(friendCount==0){
			noFriend.setVisibility(View.VISIBLE);
			
		}else {
			noFriend.setVisibility(View.GONE);
			
			FullFriendItemAdapter adapter = new FullFriendItemAdapter(friendList,this);
			friendListView.setAdapter(adapter);
			
	//		friendListView.setOnItemClickListener(new OnItemClickListenerImpl());
			
			
		}
		
		
	}
	

	
	private class OnItemClickListenerImpl implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?>parent, View view, int position,
				long id) {
			
			System.out.println("----------click--------------");
//			System.out.println(list.get(position).toString());
//			
//			list.get(position);
//			Intent intent = new Intent();    
//            intent.setClass(ListTopicActivity.this, TopicDetailActivity.class); 		
//			intent.putExtra(Constants.INTENT_EXTRA_TOPIC_DETAIL,list.get(position).toString() );
//			startActivity(intent);
//		
			
			
		}
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_addFriend:
			Intent AddFriend = new Intent(this, AddFriendActivity.class);
			startActivity(AddFriend);
			break;
		case R.id.action_searchFriend:
			
		//	FriendListActivity.this.finish();
//			friendList = getFriendList();
//			Intent SearchFriend = new Intent(this, MyFriendSearchActivity.class);
//			SearchFriend.putExtra("friendList", (Serializable) friendList); 
//			startActivity(SearchFriend);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public List<Friend> getFriendList(){
		List<Friend> myFriendList = new ArrayList<Friend>();
		User user = userDao.find();
		String tokenString = user.getToken();
		
		String friendListResult;		
		String urlString="/service/friend/listFriend?token="+tokenString;
		try {
			friendListResult = connect.testURLConn1(urlString);
			JSONObject jsonObject = new JSONObject(friendListResult);
			JSONArray jsonArray = jsonObject.getJSONArray("friendList");
			
			System.out.println("friendListResult--" + friendListResult);
			friendCount = jsonObject.getInt("num");
			for(int i=0;i<jsonArray.length();i++){   
	            JSONObject jo = (JSONObject)jsonArray.opt(i);
	            String id = jo.getString("_id");
	            String account = jo.getString("account");
	            String head = jo.getString("head");
	            Friend friend = new Friend(id, account, head);
	            myFriendList.add(friend);
	        }
			
			System.out.println("myFriendList--" + myFriendList.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return myFriendList;
	}
	
	public List<FriendMessage> getMessgaeList(){
		List<FriendMessage> friendMessageList = new ArrayList<FriendMessage>();
		User user = userDao.find();
		String tokenString = user.getToken();
		
		String friendMessageListResult;		
		String urlString="/service/friend/getFriendMessage?token="+tokenString;
		try {
			friendMessageListResult = connect.testURLConn1(urlString);
			JSONObject jsonObject = new JSONObject(friendMessageListResult);
			System.out.println("friendMessageListResult--" + friendMessageListResult);
			JSONArray jsonArray = jsonObject.getJSONArray("fMlists");
			System.out.println("fmlist" + jsonArray.length());
			for(int i=0;i<jsonArray.length();i++){   
	            JSONObject jo = (JSONObject)jsonArray.opt(i);	
	        	System.out.println("jo--" + jo);
	            String date = jo.getString("date");
	            String status = jo.getString("status");
	            String id = jo.getString("_id");
	            System.out.println("message id" +id);
	            
	            String from_id = jo.getJSONObject("from").getString("_id");
	        
	            String from_account = jo.getJSONObject("from").getString("account");
	            String from_head = jo.getJSONObject("from").getString("head");	       
	            Friend from = new Friend(from_id, from_account, from_head);
	            System.out.println("from id" +from_id +" "+ from_account+"" +from_head);
	            
	            String to_id = jo.getJSONObject("to").getString("_id");
	     
	            String to_account = jo.getJSONObject("to").getString("account");
	            System.out.println("to account" +to_account);
	            String to_head = jo.getJSONObject("to").getString("head");
	            Friend to = new Friend(to_id,to_account,to_head);
	            System.out.println("to id" +to_id +" "+ to_account+"" +to_head);
	               
	            FriendMessage friendMessage = new FriendMessage(from, to, date, status,id);
	            friendMessageList.add(friendMessage);
	        }
			
			System.out.println("list count ---" + friendMessageList.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return friendMessageList;
	}
}
