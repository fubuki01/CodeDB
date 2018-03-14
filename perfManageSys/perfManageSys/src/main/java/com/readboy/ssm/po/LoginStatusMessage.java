package com.readboy.ssm.po;

import com.readboy.ssm.po.User;

public class LoginStatusMessage {
	
	private User user;
	private String token;
	public User getUser() {
		return user;
	}
	public String getToken() {
		return token;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
