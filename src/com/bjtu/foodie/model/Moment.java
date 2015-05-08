package com.bjtu.foodie.model;

import java.util.Date;

public class Moment {

	private String id;
	private String userName;
	private int userPic;
	private String content;
	private int pic;
	private int likeCount;
	private Date date;

	public Moment(String id, String userName, int userPic, String content,
			int pic, Date date) {
		super();
		this.id = id;
		this.userName = userName;
		this.userPic = userPic;
		this.content = content;
		this.pic = pic;
		this.date = date;
	}

	public Moment(String id, String content, int pic, int likeCount) {
		super();
		this.id = id;
		this.content = content;
		this.pic = pic;
		this.likeCount = likeCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPic() {
		return pic;
	}

	public void setPic(int pic) {
		this.pic = pic;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@SuppressWarnings("deprecation")
	public String getSimpleDate() {
		String simpleDate = null;
		simpleDate = this.getMonth() + " " + this.date.getDate() + "th "
				+ (this.date.getYear() + 1900) + " " + this.date.getHours()
				+ ":" + this.date.getMinutes() + ":" + this.date.getSeconds();
		return simpleDate;
	}

	public String getMonth() {
		String month = null;
		switch (date.getMonth()) {
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
