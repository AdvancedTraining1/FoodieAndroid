package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.utils.MomentTalkToServer;

public class MomentAddActivityLast extends Activity{

	private String serverPicPath;
	
	private EditText contentEditText;
	private TextView locationTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moment_add_last);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		serverPicPath = intent.getStringExtra(Constants.KEY_PHOTO_PATH);
		contentEditText = (EditText)findViewById(R.id.content);
		locationTextView = (TextView)findViewById(R.id.location);
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
		if(LoginActivity.token != null){
			String content = contentEditText.getText().toString();
			String location = locationTextView.getText().toString();
			if(location.equals("LOCATION")){
				location = null;
			}
			
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair(Constants.POST_TOKEN, LoginActivity.token));
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
}


