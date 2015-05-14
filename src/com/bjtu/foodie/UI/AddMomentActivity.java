package com.bjtu.foodie.UI;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.model.Moment;
import com.bjtu.foodie.utils.MomentTalkToServer;

public class AddMomentActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_moment);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_moment, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();
		switch (item_id) {
		case R.id.new_action_send:
			Toast.makeText(this, "Sending~~", Toast.LENGTH_SHORT).show();
			Moment moment = new Moment("just test 1");
			AddMomentTask task = new AddMomentTask();
			task.moment = moment;
			task.execute();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void chooseImage(View view){
		Toast.makeText(getApplicationContext(), "choose image",
			     Toast.LENGTH_SHORT).show();
	}
	
	
}

class AddMomentTask extends AsyncTask<Object, Object, Object>{
	Moment moment;

	@Override
	protected Object doInBackground(Object... arg0) {
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("token", "554d7c41824fb683430ff4e1"));
        postParameters.add(new BasicNameValuePair("content", moment.getContent()));
        System.out.println("----------------------"+moment.getContent());
        MomentTalkToServer.momentPost("moment/addMoment", postParameters);
		return null;
	}
}