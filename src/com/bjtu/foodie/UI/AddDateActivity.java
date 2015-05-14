package com.bjtu.foodie.UI;

import com.bjtu.foodie.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AddDateActivity extends Activity {
	
	private Button select;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_date);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		select=(Button)findViewById(R.id.select);
		
		select.setOnClickListener(new OnClickListener() {
            @Override
			public void onClick(View v) {
                Toast.makeText(AddDateActivity.this, "select friends",Toast.LENGTH_LONG ).show();
                Intent intent = new Intent();
                intent.setClass(AddDateActivity.this, SelectDateFriendsActivity.class);
                startActivityForResult(intent, 0);
            }
        });
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
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
