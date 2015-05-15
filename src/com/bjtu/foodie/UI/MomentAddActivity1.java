package com.bjtu.foodie.UI;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bjtu.foodie.R;
import com.bjtu.foodie.common.Constants;

public class MomentAddActivity1 extends Activity{

	public static final int SELECT_PIC_BY_TAKE_PHOTO = 1; 
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	private Uri photoUri;
	private String picPath;
	private Intent newIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moment_add_1);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public void takePhoto(View view){
		Toast.makeText(getApplicationContext(), "takePhoto",
			     Toast.LENGTH_SHORT).show();
		
	    String SDState = Environment.getExternalStorageState();  
        if(SDState.equals(Environment.MEDIA_MOUNTED))  
        {  
              
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"  
            /*** 
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图 
             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰 
             */ 
            ContentValues values = new ContentValues();    
            photoUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);    
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, SELECT_PIC_BY_TAKE_PHOTO);  
        }else{  
            Toast.makeText(this,"内存卡不存在", Toast.LENGTH_LONG).show();  
        }
		
	}
	
	public void choosePhoto(View view){
		Toast.makeText(getApplicationContext(), "choosePhoto",
			     Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();  
        intent.setType("image/*");  
        intent.setAction(Intent.ACTION_GET_CONTENT);  
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);  
	}
	
	@Override  
    public boolean onTouchEvent(MotionEvent event) {  
        finish();  
        return super.onTouchEvent(event);  
    } 
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK)  
        {
            doPhoto(requestCode,data);  
        }  
        super.onActivityResult(requestCode, resultCode, data);  
    }
	
	private void doPhoto(int requestCode,Intent data)  
    {
        if(requestCode == SELECT_PIC_BY_PICK_PHOTO )  //从相册取图片，有些手机有异常情况，请注意  
        {  
            if(data == null)  
            {  
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();  
                return;  
            }  
            photoUri = data.getData();  
            if(photoUri == null )  
            {  
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();  
                return;  
            }  
        }  
        String[] pojo = {MediaStore.Images.Media.DATA};  
        Cursor cursor = managedQuery(photoUri, pojo, null, null,null);     
        if(cursor != null )  
        {  
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);  
            cursor.moveToFirst();  
            picPath = cursor.getString(columnIndex);
            if(VERSION.SDK_INT < 14) {  
                cursor.close();  
             }
        }  
        Log.i(Constants.TAG_MOMENT, "imagePath = "+picPath);  
        if(picPath != null && ( picPath.endsWith(".png") || picPath.endsWith(".PNG") ||picPath.endsWith(".jpg") ||picPath.endsWith(".JPG")  ))  
        {
        	newIntent = new Intent(this, MomentAddActivity2.class);
        	newIntent.putExtra(Constants.KEY_PHOTO_PATH, picPath);
        	if(requestCode == SELECT_PIC_BY_TAKE_PHOTO){
        		newIntent.putExtra(Constants.KEY_PHOTO_FROM_TAKE, "takePhoto");
        	}
        	Log.i(Constants.TAG_MOMENT,picPath);
            setResult(Activity.RESULT_OK, newIntent);  
            startActivity(newIntent);
        }else{  
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();  
        }  
    }
}
