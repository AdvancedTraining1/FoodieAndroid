package com.bjtu.foodie.utils;

import android.R.integer;
import android.content.Context;
import android.view.WindowManager;

public class Util {

	/** 
     * 得到设备屏幕的宽度 
     */  
    public static int getScreenWidth(Context context) {  
        return context.getResources().getDisplayMetrics().widthPixels;  
    }  
  
    /** 
     * 得到设备屏幕的高度 
     */  
    public static int getScreenHeight(Context context) {  
        return context.getResources().getDisplayMetrics().heightPixels;  
    }  
  
    /** 
     * 得到设备的密度 
     */  
    public static float getScreenDensity(Context context) {  
        return context.getResources().getDisplayMetrics().density;  
    }  
  
    /** 
     * 把密度转换为像素 
     */  
    public static int dip2px(Context context, float px) {  
        final float scale = getScreenDensity(context);  
        return (int) (px * scale + 0.5);  
    }
    
    public static int getScreenWidthDp(Context context){
    	return  ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();

    }
}
