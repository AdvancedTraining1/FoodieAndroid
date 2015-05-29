package com.bjtu.foodie.model;

public class User {

	private String id;
	private String name; //real name
	// account[] email password type[restaurant client] sex avatar tag[interesting] friends
	// flag[is deleted] friendsCount
	// token 
	private String username;
	private String email;
	private String password;
	private String head;
	private String token;
	
	//add userId
	private String userid;
	
	public User(String username,  String email, String password){
		this.username = username;
		this.email = email;
		this.password = password;
	}
	public User(){}
	
	public User(String token, String id){
		this.token = token;
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getUserId() {
		return userid;
	}
	public void setUserId(String userId) {
		this.userid = userId;
	}

	
	
}
