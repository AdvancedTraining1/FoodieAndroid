package com.bjtu.foodie.UI;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import com.bjtu.foodie.R;
import com.bjtu.foodie.adapter.FullMomentItemAdapter;
import com.bjtu.foodie.data.TestData;
import com.bjtu.foodie.map.MapActivity;
import com.bjtu.foodie.model.Moment;

public class MomentsActivity extends Activity {

	// Listview adapter List<moments> List<comment>
	private ListView lv_allMoments;
	private List<Moment> list_moments;
	private FullMomentItemAdapter momentAdapter;

	private TextView tv_username;
	private ImageView iv_userPic;
	
	private ShareActionProvider myProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_moments);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		lv_allMoments = (ListView) findViewById(R.id.lv_moments);
		tv_username = (TextView) findViewById(R.id.tv_username);
		iv_userPic = (ImageView) findViewById(R.id.iv_userPic);
		
		list_moments = (new TestData()).getMomentsData();
		momentAdapter = new FullMomentItemAdapter(this, list_moments);

		lv_allMoments.setAdapter(momentAdapter);

		tv_username.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentMyMoment = new Intent(getApplicationContext(),
						MyMomentActivity.class);
				
				startActivity(intentMyMoment);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.moments, menu);
		
		MenuItem shareItem = menu.findItem(R.id.action_share);
		myProvider = (ShareActionProvider) shareItem.getActionProvider();
		myProvider.setShareIntent(getDefaultIntent());
		
		return true;
	}

	private Intent getDefaultIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		return shareIntent;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_addMoments:
			Intent intentNewMoment = new Intent(this, MomentAddActivity1.class);
			startActivity(intentNewMoment);
			break;
		case R.id.action_map:
			Intent intentMap = new Intent(this, MapActivity.class);
			startActivity(intentMap);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
