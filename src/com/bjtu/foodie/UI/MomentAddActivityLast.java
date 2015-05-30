package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.db.UserDao;
import com.bjtu.foodie.map.GetCurrentLocateAround;
import com.bjtu.foodie.model.User;
import com.bjtu.foodie.utils.MomentTalkToServer;

public class MomentAddActivityLast extends Activity{

	private String serverPicPath;
	
	private EditText contentEditText;
	private static TextView locationTextView;
	public UserDao userDao = new UserDao(this);
	private static String curLocName;
	private static Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moment_add_last);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		context = MomentAddActivityLast.this;
		
		Intent intent = getIntent();
		serverPicPath = intent.getStringExtra(Constants.KEY_PHOTO_PATH);
		contentEditText = (EditText)findViewById(R.id.content);
		locationTextView = (TextView)findViewById(R.id.location);
		
		locationTextView.setOnClickListener(new OnGetLocationClickListener());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.moment_add_last_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	finish();
                return true;
            case R.id.menu_publish:
            	createMoment();
        }
        return super.onOptionsItemSelected(item);
    }
	
	public void createMoment(){
		User user = userDao.find();
		if(user!=null){
			String content = contentEditText.getText().toString();
			String location = locationTextView.getText().toString();
			if(location.equals("LOCATION")){
				location = null;
			}
			
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair(Constants.POST_TOKEN, user.getToken()));
	        postParameters.add(new BasicNameValuePair(Constants.POST_CONTENT, content));
	        postParameters.add(new BasicNameValuePair(Constants.POST_LOCATION, location));
	        postParameters.add(new BasicNameValuePair(Constants.POST_PICTURE, serverPicPath));
	        
	        String resultString = MomentTalkToServer.momentPost("moment/addMoment",postParameters);
	        if(resultString.equals("create moment success！")){
	        	Toast.makeText(getApplicationContext(), "create success !",
					     Toast.LENGTH_SHORT).show();
	        	
	        	MomentAddActivity1.instance.finish();
	        	MomentAddActivity2.instance.finish();
	        	MomentsActivity.instance.finish();
	        	finish();
	    		Intent intent = new Intent(MomentAddActivityLast.this, MomentsActivity.class);
	    		startActivity(intent);
	        }
		}else{
			Intent loginIntent = new Intent(this,LoginActivity.class);
			startActivity(loginIntent);
		}
	}
	
	class OnGetLocationClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			GetCurrentLocateAround mGetCurrentLocate = new GetCurrentLocateAround(
					getApplicationContext());
		}
	}
	
	public static void showNearbyList(final ArrayList<String> nearbyList) {
		
		CharSequence[] items = nearbyList.toArray(new CharSequence[nearbyList.size()]);
		new AlertDialog.Builder(context).setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				curLocName = nearbyList.get(which);
				locationTextView.setText(curLocName);
				dialog.dismiss();
			}
		}).show();
		
	}
}


