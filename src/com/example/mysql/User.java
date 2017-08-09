package com.example.mysql;

public class User {
	String name, username, password;
	int age;
	
	public User(String name, String username, String password){
		this.name = name;
		this.username=username;
		this.password=password;
	}
	
	public User( String username, String password){
		this.username=username;
		this.password=password;
	}

	public String GetUserName (User user ){
		String userName = user.username;
		
		return userName;
	}
}
