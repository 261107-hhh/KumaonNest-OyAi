package com.example.register.Service;

import com.example.register.Dto.UserDto;
import com.example.register.Entity.User;
import com.example.register.Payload.UserResponse;
import com.example.register.Payload.UserUpdateRequest;
import com.example.register.Payload.request.SignupRequest;


public interface UserService {

	User createUser(SignupRequest userDto);

	boolean checkEmail(String email);

	UserResponse removeUser(Long id);

	boolean sendMail(String to, String subject, String body);

	void setOtp(String otp, String mail);

	void updatePassword(String mail, String password);

	UserResponse getAllUsers();

//	UserDto getUsers(int id);

	String updateUser(Long id, UserUpdateRequest user);

	UserDto getUsers(String email);

}
