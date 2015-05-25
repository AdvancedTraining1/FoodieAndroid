package com.bjtu.foodie.model;

import java.io.Serializable;

public class Friend implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String account;
	private String head;
	private boolean click= false;
	
	public Friend(String id, String account, String head) {
		super();
		this.id = id;
		this.account = account;
		this.head = head;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public boolean isClick() {
		return click;
	}

	public void setClick(boolean click) {
		this.click = click;
	}
	
	
	
	
	
	
	

	

}
