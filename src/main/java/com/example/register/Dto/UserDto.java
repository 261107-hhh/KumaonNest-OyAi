package com.example.register.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDto {

	@NotBlank
	@Size(min = 11, max = 50)
	private String email;

//	@NotBlank
//	@Size(min = 8, max = 50)
//	private String password;

	private String username;

	private String phone;

	public UserDto() {
		super();
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}