package com.example.register.Payload;

import java.util.Set;

import com.example.register.Entity.Role;

public class UserUpdateRequest {

	private String name;
	private String email;
	private String phone;
	private String address;
	private boolean active;
	private boolean verify;
	private Set<Role> role;

	public UserUpdateRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserUpdateRequest(String name, String email, String phone, String address, boolean active, boolean verify,
			Set<Role> role) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.active = active;
		this.verify = verify;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isVerify() {
		return verify;
	}

	public void setVerify(boolean verify) {
		this.verify = verify;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}


}
