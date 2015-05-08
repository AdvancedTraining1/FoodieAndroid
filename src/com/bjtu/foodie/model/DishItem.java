package com.bjtu.foodie.model;


import java.util.Calendar;
import java.util.Date;

import android.R.integer;
import android.text.format.Time;

public class DishItem {

	private String id;
	private String dishName;
	private String desc;
	private int pic;
	private int color;

	public DishItem(String id, String dishName,String desc,int pic,int color) {
		super();
		this.id = id;
		this.dishName = dishName;
		this.desc = desc;
		this.pic = pic;
		this.color = color;
	}

	public int getColor() {
		return color;
	}
	
	
	public void setColor(int color) {
		this.color =color;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getPic() {
		return pic;
	}

	public void setPic(int pic) {
		this.pic = pic;
	}


	public String getName() {
		return dishName;
	}

	public void setName(String dishName) {
		this.dishName = dishName;
	}


}

