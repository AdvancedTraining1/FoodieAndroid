package com.bjtu.foodie.db;

import java.util.List;

import com.bjtu.foodie.model.User;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao {
	private UserSQLiteOpenHelper dbHelper;
	public  UserDao(Context contex) {
		dbHelper = new UserSQLiteOpenHelper(contex);
	}
	
	public void add(String token,int role){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("insert into user (token,role) values (?,?)", new Object[]{token,role});
		db.close();
	}
	
	public User find(){
		User user = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select token, role from user", null);
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
