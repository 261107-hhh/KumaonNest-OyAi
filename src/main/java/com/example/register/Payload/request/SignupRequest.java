package com.example.register.Payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {

	@NotBlank
	@Size(min = 11, max = 50)
	private String email;

	@NotBlank
	@Size(min = 8, max = 50)
	private String password;

	private String username;

	private String phone;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public SignupRequest(@NotBlank @Size(min = 11, max = 50) String email,
			@NotBlank @Size(min = 8, max = 50) String password, String username, String phone) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
		this.phone = phone;
	}



}