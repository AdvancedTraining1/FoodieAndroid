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
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.FullDateFriendItemsAdapter;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.data.TestData;
import com.bjtu.foodie.model.User;
import com.bjtu.foodie.utils.DatesTalkToServer;

public class SelectDateFriendsActivity extends Activity implements
		OnClickListener {

	private ListView m_listview;
	private FullDateFriendItemsAdapter m_adapter;
	private Button m_submit;
	private String friendchoose = "friend choose";
	private static final int MESSAGE_SENT = 1;

	ArrayList<JSONObject> list = new ArrayList<JSONObject>();
	//List<User> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datefriend);
		
		
		ListFriendTask task = new ListFriendTask();
		m_listview = (ListView) findViewById(R.id.friends);
		
		task.listView = m_listview;
		task.activity = this;
		task.list = list;
		task.execute();
		
		m_submit = (Button) findViewById(R.id.button0);
		m_submit.setOnClickListener(this);			
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button0:
			String s="";
			ArrayList<Boolean> checkList = m_adapter.getChecklist();System.out.println("checkList=="+checkList);
			ArrayList<String> idList = m_adapter.getIDList();System.out.println("idList=="+idList);
			ArrayList<String> nameList = m_adapter.getNameList();System.out.println("nameList=="+nameList);
			ArrayList<Object> item = null;
			
			  for(int i=0;i<checkList.size();i++){ 
				  if(checkList.get(i)){
					  s=s+idList.get(i)+","; 
					  //item.add(m_adapter.getItem(i));
				  } 
			  }
			  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show(); 

			Intent intent = new Intent(this, AddDateActivity.class);
			intent.putStringArrayListExtra("friendIdList", idList);
			intent.putStringArrayListExtra("friendNameList", nameList);
			//intent.putExtra("friendList", idList);// add constant variable
													// friendList??
			intent.putExtra(Constants.flag, "have selected friends");
			
			startActivity(intent);

			break;
		}

	}

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_SENT:
                Toast.makeText(getApplicationContext(), "Message sent!", Toast.LENGTH_LONG).show();
                break;
            }
        }
    };

	
    public String selectFriend(){
    	String resultString=null;
    	
		if (LoginActivity.token != null) {
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair(Constants.POST_TOKEN, LoginActivity.token));

			resultString = DatesTalkToServer.datesPost("date/select", postParameters);
			
		} else {
			Intent loginIntent = new Intent(this, LoginActivity.class);
			startActivity(loginIntent);
		}
		
		return resultString;
	}
	
    class ListFriendTask extends AsyncTask<Object, Object, Object>{
		ListView listView;
		SelectDateFriendsActivity activity;
		ArrayList<JSONObject> list;
		//List<User> list;
		
		@Override
		protected Object doInBackground(Object... arg0) {
			String friendResult = selectFriend();
			System.out.println("friendResult====="+friendResult);
			try {
				JSONObject jsonObject = new JSONObject(friendResult);System.out.println(jsonObject);
				JSONArray jsonArray = jsonObject.getJSONArray("root");System.out.println(jsonArray);
				for(int i=0;i<jsonArray.length();i++){   
	                JSONObject jo = (JSONObject)jsonArray.opt(i);
	                //User jo = (User)jsonArray.opt(i);System.out.println(jo);
	                //User jb = (User)JSONObject.toBean(jo,User.class);//将json对象转换为User对象
	                list.add(jo);
	            }
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		public void onPostExecute(Object result){
			
			m_adapter = new FullDateFriendItemsAdapter(activity, list);
			m_listview.setAdapter(m_adapter);

		}
	}
    
}

