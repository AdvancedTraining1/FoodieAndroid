package com.bjtu.foodie.db;

import com.bjtu.foodie.model.User;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao {
	private UserSQLiteOpenHelper dbHelper;
	public  UserDao(Context contex) {
		dbHelper = new UserSQLiteOpenHelper(contex);
	}
	
	public void add(String token){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("insert into user (token) values (?)", new Object[]{token});
		db.close();
	}
	
	public User find(){
		User user = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select token from user", null);
		if(cursor.moveToNext()){
			String token = cursor.getString(cursor.getColumnIndex("token"));
			user = new User(token);		
		}
		cursor.close();
		db.close();
		return user;
	}
	public void delete (String token){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("delete from user where token = ?", new Object[]{token});
		db.close();
	}

}
