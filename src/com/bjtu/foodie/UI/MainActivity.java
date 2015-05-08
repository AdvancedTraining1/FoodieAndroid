package com.bjtu.foodie.UI;

import com.bjtu.foodie.R;
import android.view.View.OnClickListener;
import cn.jpush.android.api.JPushInterface;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private Button m_ToDish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		m_ToDish = (Button)findViewById(R.id.button_dish);
		
		
		JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        
        
        
        m_ToDish.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "ToDish",Toast.LENGTH_LONG ).show();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DishMenu.class);
                startActivityForResult(intent, 0);
            }
		});
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
	
	@Override
    public void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
	
	public void onPause()
	{
		super.onPause();
		JPushInterface.onPause(this);
	}
}
