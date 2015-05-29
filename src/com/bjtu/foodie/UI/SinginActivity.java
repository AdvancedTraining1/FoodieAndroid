package com.bjtu.foodie.UI;

import com.bjtu.foodie.R;
import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ViewAnimator;

public class SinginActivity extends SampleActivityBase {
	public static final String TAG = "MainActivity";
	//private boolean mLogShown;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_singin);
		
		android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CardEmulationFragment fragment = new CardEmulationFragment();
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();
        
        //MessageStorage.GetInstance().GetMsg();
        
	}
	
	 @Override
	    protected  void onStart() {
	        super.onStart();
	}
	 
	 

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//	
//	 @Override
//	    public boolean onPrepareOptionsMenu(Menu menu) {
//	        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
//	        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
//	        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);
//
//	        return super.onPrepareOptionsMenu(menu);
//	    }
//
//	    @Override
//	    public boolean onOptionsItemSelected(MenuItem item) {
//	        switch(item.getItemId()) {
//	            case R.id.menu_toggle_log:
//	                mLogShown = !mLogShown;
//	                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
//	                if (mLogShown) {
//	                    output.setDisplayedChild(1);
//	                } else {
//	                    output.setDisplayedChild(0);
//	                }
//	                supportInvalidateOptionsMenu();
//	                return true;
//	        }
//	        return super.onOptionsItemSelected(item);
//	    }

}