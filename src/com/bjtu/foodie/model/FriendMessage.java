package com.bjtu.foodie.model;

import java.io.Serializable;

public class FriendMessage implements Serializable{
	
	Friend from;
	Friend to;
	String date;
	String status;
	String id;
	

	
	public FriendMessage(Friend from, Friend to, String date, String status,String id) {
		super();
		this.from = from;
		this.to = to;
		this.date = date;
		this.status = status;
		this.id = id;
	}
	public Friend getFrom() {
		return from;
	}
	public void setFrom(Friend from) {
		this.from = from;
	}
	public Friend getTo() {
		return to;
	}
	public void setTo(Friend to) {
		this.to = to;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
	
	

}
