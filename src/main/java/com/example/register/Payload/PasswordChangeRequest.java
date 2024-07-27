package com.example.register.Payload;

public class PasswordChangeRequest {

	private String email;
	private String password;
	private String otp;

	public PasswordChangeRequest() {
		super();
	}

	public PasswordChangeRequest(String email, String password, String otp) {
		super();
		this.email = email;
		this.password = password;
		this.otp = otp;
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

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}



}
