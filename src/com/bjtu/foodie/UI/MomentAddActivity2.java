package com.bjtu.foodie.UI;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.widget.ImageView;

import com.bjtu.foodie.R;

public class MomentAddActivity2 extends Activity{

	ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moment_add_2);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Intent intent = getIntent();
		String picPath = intent.getStringExtra(MomentAddActivity1.KEY_PHOTO_PATH);
		System.out.println("---path"+picPath);
		imageView = (ImageView) findViewById(R.id.newImage);
		
		int orientation = readPictureDegree(picPath);//获取旋转角度
		System.out.println("orientation==="+orientation);
		if(Math.abs(orientation) > 0){
			Bitmap bitmap = BitmapFactory.decodeFile(picPath);
			Bitmap bm = rotateBitmap(orientation, bitmap);//旋转图片
			imageView.setImageBitmap(bm);
		}
		
		
		
        
	}
	
	public static int readPictureDegree(String path) {  
		int degree  = 0;  
		try {
		ExifInterface exifInterface = new ExifInterface(path);  
		int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);  
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
	
	public static Bitmap rotateBitmap(int degree, Bitmap bitmap) {  
	    Matrix matrix = new Matrix();  
	    matrix.postRotate(degree);  
	  
	    Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),  
	            bitmap.getHeight(), matrix, true);  
	    return bm;  
	}
}
