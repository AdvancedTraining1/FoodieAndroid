package com.bjtu.foodie.UI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.utils.MomentTalkToServer;
import com.bjtu.foodie.utils.UploadUtil;

public class MomentAddActivityLast extends Activity{

	//private Bitmap bitmap;
	private String picPath;
	private int orientation;
	private String serverPicPath;
	
	private EditText contentEditText;
	private TextView locationTextView;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moment_add_last);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent intent = getIntent();
		picPath = intent.getStringExtra(Constants.KEY_PHOTO_PATH);
		orientation = intent.getIntExtra(Constants.KEY_ORIENTATION, 0);
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
            	//finish();
        }
        return super.onOptionsItemSelected(item);
    }
	
	public void createMoment(){
		Toast.makeText(getApplicationContext(), "SEND~~~~~~~~~~",
			     Toast.LENGTH_SHORT).show();
		
		UploadPicTask uploadPicTask = new UploadPicTask(picPath);
		uploadPicTask.execute();
		
		String content = contentEditText.getText().toString();
		String location = locationTextView.getText().toString();
		if(location.equals("LOCATION")){
			location = null;
		}
		
		if(LoginActivity.token != null){
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
	        postParameters.add(new BasicNameValuePair(Constants.POST_TOKEN, LoginActivity.token));
	        postParameters.add(new BasicNameValuePair(Constants.POST_CONTENT, content));
	        postParameters.add(new BasicNameValuePair(Constants.POST_LOCATION, location));
	        postParameters.add(new BasicNameValuePair(Constants.POST_PICTURE, serverPicPath));
	        
	        String resultString = MomentTalkToServer.momentPost("moment/create",postParameters);
	        if(resultString.equals("create recipe success！")){
	        	Toast.makeText(getApplicationContext(), "create success !",
					     Toast.LENGTH_SHORT).show();
	        	finish();
	    		Intent intent = new Intent(this,MomentsActivity.class);
	    		startActivity(intent);
	        }
		}
		else{
			Intent loginIntent = new Intent(this,LoginActivity.class);
			startActivity(loginIntent);
		}
	}
	
	class UploadPicTask extends AsyncTask<Object, Object, String>{
		private String local;
		public UploadPicTask(String picPath) {
			local = picPath;
		}
		
		@Override
		protected String doInBackground(Object... arg0) {
			
			final File file = new File(local);
	        if (file != null) {
	        	String result = UploadUtil.uploadFile(file, Constants.URL_UPLOADE);
	        	System.out.println("---path-result---"+result);
	        	return result;
			}
			return null;
		}
		
		@Override
		public void onPostExecute(String result){
			System.out.println("---------------------"+result);
			serverPicPath = result;
		}
	}
	
	class CreateMomentTask extends AsyncTask<Object, Object, Object>{

		@Override
		protected Object doInBackground(Object... arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}


