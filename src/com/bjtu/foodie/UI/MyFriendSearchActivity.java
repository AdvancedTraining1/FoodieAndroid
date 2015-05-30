package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bjtu.foodie.R;
import com.bjtu.foodie.R.id;
import com.bjtu.foodie.R.layout;
import com.bjtu.foodie.R.menu;
import com.bjtu.foodie.adapter.FullFriendItemAdapter;
import com.bjtu.foodie.model.Friend;
import com.bjtu.foodie.model.FriendMessage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyFriendSearchActivity extends Activity {
	

	EditText eSearch;
	ImageView ivDeleteText;
	ListView mListView;
	
	//ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
	
	ArrayList<String> musername = new ArrayList<String>();
	ArrayList<String> mListText = new ArrayList<String>();
	List<Friend> mfriendList =new ArrayList<Friend>();
	List<Friend> friendList =new ArrayList<Friend>();
	
	SimpleAdapter adapter;
	
	Handler myhandler = new Handler();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_friend_search);
		
		friendList = (List<Friend>) getIntent().getSerializableExtra("friendList");
		
		mfriendList = (List<Friend>) getIntent().getSerializableExtra("friendList");
		
		
		set_eSearch_TextChanged();
	        
	    set_ivDeleteText_OnClick();
	        
	    set_mListView_adapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_friend_search, menu);
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
	
	 private void set_mListView_adapter()
	    {
	    	mListView = (ListView) findViewById(R.id.mListView);
	     
	    	getmData(friendList);
	        FullFriendItemAdapter adapter = new FullFriendItemAdapter(mfriendList,this);
	        mListView.setAdapter(adapter);
//	        adapter = new SimpleAdapter(this,mData,android.R.layout.simple_list_item_2, 
//				    new String[]{"title","text"},new int[]{android.R.id.text1,android.R.id.text2});
//		    
//	        mListView.setAdapter(adapter);
	    }
	    
	    /**
	     * ������������ı����ʱ�ļ�����
	     */
	    private void set_eSearch_TextChanged()
	    {
	    	eSearch = (EditText) findViewById(R.id.etSearch);
	    	
	    	eSearch.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
					// TODO Auto-generated method stub
					//���Ӧ�����ڸı��ʱ������Ķ����ɣ����廹û�õ���
				}
				
				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub
					//�����ı���ı�֮ǰ��ִ�еĶ���
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					/**�����ı���ı�֮�� ��ִ�еĶ���
					 * ��Ϊ����Ҫ���ľ��ǣ����ı���ı��ͬʱ�����ǵ�listview�����Ҳ������Ӧ�ı䶯��������һ����ʾ�ڽ����ϡ�
					 * �����������Ǿ���Ҫ������ݵ��޸ĵĶ����ˡ�
					 */
					if(s.length() == 0){
						ivDeleteText.setVisibility(View.GONE);//���ı���Ϊ��ʱ��������ʧ
					}
					else {
						ivDeleteText.setVisibility(View.VISIBLE);//���ı���Ϊ��ʱ�����ֲ��
					}
					
					myhandler.post(eChanged);
				}
			});
	    	
	    }
	    
	    
	    
	    Runnable eChanged = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String data = eSearch.getText().toString();
				
				mfriendList.clear();//��Ҫ��գ���Ȼ�����
				friendList = (List<Friend>) getIntent().getSerializableExtra("friendList");
				System.out.println("inchange ---" + friendList.size());
				
				getmDataSub(mfriendList, friendList,data);//��ȡ�������
				
				adapter.notifyDataSetChanged();//����
				
			}
		};
		
		/**
		 * ��ø������������data����Ԫ���ɸѡ��ɸѡ��������ݷ���mDataSubs��
		 * @param mDataSubs
		 * @param data
		 */
		
		private void getmDataSub(List<Friend> mDataSubs,List<Friend> friendList, String data)
		{
			List<Friend> friendList2 = new ArrayList<Friend>();
	    	friendList2 = friendList;
			int length = musername.size();
			System.out.println("fiendList size --- " + friendList2.size());
			for(int i = 0; i < length; ++i){
				if(musername.get(i).contains(data)){
					Friend fiend = friendList2.get(i);
					
			        mDataSubs.add(fiend);
				}
			}
		}
		
		/**
		 * ���ò��ĵ���¼�������չ���
		 */
	    
	    private void set_ivDeleteText_OnClick()
	    {
	    	ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
	    	ivDeleteText.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					eSearch.setText("");
				}
			});
	    }
	    
	    /**
	     * ���Ԫ��� ����ʼ��mDatas
	     * @param mDatas
	     */

	    private void getmData(List<Friend> myfriendlist)
	    {
	    	List<Friend> friendList = new ArrayList<Friend>();
	    	friendList = myfriendlist;
	    	for (int i = 0; i < friendList.size(); i++) {   		
	    		System.out.println(i);
				Friend friend = friendList.get(i);
	    		musername.add(friend.getAccount());				
			}
	    	
	    }
}
