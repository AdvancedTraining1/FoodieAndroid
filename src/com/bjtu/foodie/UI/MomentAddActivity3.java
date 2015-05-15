package com.bjtu.foodie.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bjtu.foodie.R;
import com.bjtu.foodie.common.Constants;

public class MomentAddActivity3 extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moment_add_3);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
}
