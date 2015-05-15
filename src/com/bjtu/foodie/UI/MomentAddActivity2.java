package com.bjtu.foodie.UI;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bjtu.foodie.R;
import com.bjtu.foodie.common.Constants;

public class MomentAddActivity2 extends Activity {

	private ImageView imageView;
	private String picPath;
	private int orientation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moment_add_2);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		imageView = (ImageView) findViewById(R.id.newImage);

		Intent intent = getIntent();
		picPath = intent.getStringExtra(Constants.KEY_PHOTO_PATH);
		Log.i(Constants.TAG_MOMENT, "---path" + picPath);

		Bitmap bitmap = BitmapFactory.decodeFile(picPath);
		if (intent.getStringExtra(Constants.KEY_PHOTO_FROM_TAKE) != null) {
			orientation = readPictureDegree(picPath);// 获取旋转角度
			Log.i(Constants.TAG_MOMENT, "orientation===" + orientation);
			if (Math.abs(orientation) > 0) {
				Bitmap bm = rotateBitmap(orientation, bitmap);// 旋转图片
				imageView.setImageBitmap(bm);
			}
		} else {
			imageView.setImageBitmap(bitmap);
		}
	}

	public int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public Bitmap rotateBitmap(int degree, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postRotate(degree);

		Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return bm;
	}
	
	public void newPhoto(View view){
		Intent intent = new Intent(this, MomentAddActivity1.class);
		finish();
		startActivity(intent);
	}
	
	public void next(View view){
		Intent intent = new Intent(this, MomentAddActivityLast.class);
		intent.putExtra(Constants.KEY_PHOTO_PATH, picPath);
		intent.putExtra(Constants.KEY_ORIENTATION, orientation);
		startActivity(intent);
	}
}
