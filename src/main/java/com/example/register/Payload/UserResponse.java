package com.example.register.Payload;

import java.util.List;

import com.example.register.Dto.UserDto;

public class UserResponse {

	private List<UserDto> users;

	public List<UserDto> getUsers() {
		return users;
	}

	public void setUsers(List<UserDto> users) {
		this.users = users;
	}
}