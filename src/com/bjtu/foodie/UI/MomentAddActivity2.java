package com.bjtu.foodie.UI;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.common.Constants;
import com.bjtu.foodie.utils.UploadUtil;

public class MomentAddActivity2 extends Activity {

	private ImageView imageView;
	private Bitmap bitmap;
	private String picPath;
	private int orientation;
	private String newPicPath;
	private String serverPicPath;
	public static MomentAddActivity2 instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moment_add_2);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		instance = this;
		imageView = (ImageView) findViewById(R.id.newImage);

		Intent intent = getIntent();
		picPath = intent.getStringExtra(Constants.KEY_PHOTO_PATH);
		Log.i(Constants.TAG_MOMENT, "choose/taken pic path" + picPath);

		Bitmap bitmapTemp = BitmapFactory.decodeFile(picPath);
		if (intent.getStringExtra(Constants.KEY_PHOTO_FROM_TAKE) != null) {
			orientation = readPictureDegree(picPath);// 获取旋转角度
			Log.i(Constants.TAG_MOMENT, "orientation===" + orientation);
			if (Math.abs(orientation) > 0) {
				bitmap = rotateBitmap(orientation, bitmapTemp);// 旋转图片
			}else{
				bitmap = bitmapTemp;
			}
		} else {
			bitmap = bitmapTemp;
		}
		bitmap = trimBitmap(bitmap);
		newPicPath = saveImage(bitmap,70);
		imageView.setImageBitmap(bitmap);
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
	
	public Bitmap trimBitmap(Bitmap bitmap){
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap trim;
		if(width > height){
			trim = Bitmap.createBitmap(bitmap, (width-height)/2, 0, height, height);
		}else{
			trim = Bitmap.createBitmap(bitmap, 0, (height-width)/2, width, width);
		}
		trim = Bitmap.createScaledBitmap(trim, 600, 600, false);
		return trim;
	}
	
	public static String saveImage(Bitmap bitmap, int quality) {
		String fileName = "upload_" + System.currentTimeMillis() + ".jpg";
		String filePath = Environment.getExternalStorageDirectory()+"/"+ fileName;
		Log.i(Constants.TAG_MOMENT, "saved file path:"+filePath);
		String path = null;
		try {
			File file = new File(filePath);			
			FileOutputStream out = new FileOutputStream(file);
			
			BufferedOutputStream bos = new BufferedOutputStream(out);  
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos)) {
				Log.d(Constants.TAG_MOMENT, "saveImage seccess: fileName= " + filePath + ", quality = " + quality);
				bos.flush();
				out.close();
				path = filePath;
			} else {
				Log.d(Constants.TAG_MOMENT, "saveImage fail: fileName= " + filePath);
			}
		} catch (Exception e) {
			Log.d(Constants.TAG_MOMENT, "saveImage Exception: " + e);
			e.printStackTrace();
		}
		
		System.out.println(path);
		return path;
	}
	
	public void newPhoto(View view){
		Intent intent = new Intent(this, MomentAddActivity1.class);
		finish();
		startActivity(intent);
	}
	
	public void next(View view){
		UploadPicTask uploadPicTask = new UploadPicTask(newPicPath);
		uploadPicTask.execute();
		Toast.makeText(getApplicationContext(), "Image uploading, please wait...",
			     Toast.LENGTH_SHORT).show();
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
	        	Log.i(Constants.TAG_MOMENT, "server file path:"+result);
	        	return result;
			}
			return null;
		}
		
		@Override
		public void onPostExecute(String result){
			serverPicPath = result;
			Intent intent = new Intent(MomentAddActivity2.this, MomentAddActivityLast.class);
			intent.putExtra(Constants.KEY_PHOTO_PATH, serverPicPath);
			startActivity(intent);
		}
	}
}
