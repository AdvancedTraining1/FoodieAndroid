package com.bjtu.foodie.model;

import java.util.Date;

public class DateModel {

	private String userId;
	//private String restaurantId;
	private String userName;
	private int userPic;
	private String dateTitle;
	private String dateContent;
	private Date dateTime;
	private Date logTime;
	private int dateUsers_count;
	//private int dateStatus;
	
	public DateModel(String userId, String userName, int userPic, String dateTitle, String dateContent,
			Date dateTime, Date logTime, int dateUsers_count) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPic = userPic;
		this.dateTitle = dateTitle;
		this.dateContent = dateContent;
		this.dateTime = dateTime;
		this.logTime = logTime;
		this.dateUsers_count = dateUsers_count;
	}
	
	
	public String getUserId() {
		return userId;
	}




	public void setUserId(String userId) {
		this.userId = userId;
	}




	public String getUserName() {
		return userName;
	}




	public void setUserName(String userName) {
		this.userName = userName;
	}




	public int getUserPic() {
		return userPic;
	}




	public void setUserPic(int userPic) {
		this.userPic = userPic;
	}




	public String getDateTitle() {
		return dateTitle;
	}




	public void setDateTitle(String dateTitle) {
		this.dateTitle = dateTitle;
	}




	public String getDateContent() {
		return dateContent;
	}




	public void setDateContent(String dateContent) {
		this.dateContent = dateContent;
	}




	public Date getDateTime() {
		return dateTime;
	}




	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}




	public Date getLogTime() {
		return logTime;
	}




	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}




	public int getDateUsers_count() {
		return dateUsers_count;
	}




	public void setDateUsers_count(int dateUsers_count) {
		this.dateUsers_count = dateUsers_count;
	}




	@SuppressWarnings("deprecation")
	public String getSimpleDate() {
		String simpleDate = null;
		simpleDate = this.getMonth() + " " + this.logTime.getDate() + "th "
				+ (this.logTime.getYear() + 1900) + " " + this.logTime.getHours()
				+ ":" + this.logTime.getMinutes() + ":" + this.logTime.getSeconds();
		return simpleDate;
	}

	public String getMonth() {
		String month = null;
		switch (logTime.getMonth()) {
		case 1:
			month = "Jan";
			break;
		case 2:
			month = "Feb";
			break;
		case 3:
			month = "Mar";
			break;
		case 4:
			month = "Apr";
			break;
		case 5:
			month = "May";
			break;
		case 6:
			month = "June";
			break;
		case 7:
			month = "July";
			break;
		case 8:
			month = "Aug";
			break;
		case 9:
			month = "Sep";
			break;
		case 10:
			month = "Oct";
			break;
		case 11:
			month = "Nov";
			break;
		case 12:
			month = "Dec";
			break;
		}
		return month;
	}

}
