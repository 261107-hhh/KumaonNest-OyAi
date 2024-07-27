package com.example.register.Payload;

import com.example.register.Dto.UserDto;

public class Response {

	private UserDto user;
	private String token;
	private String message = "Success";


	public Response() {
		super();
	}

	public Response(UserDto user, String token, String message) {
		super();
		this.user = user;
		this.token = token;
		this.message = message;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}