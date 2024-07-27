package com.example.register.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.register.Entity.User;
import com.example.register.Repo.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

    return UserDetailsImpl.build(user);
  }

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// Load user From database
//		System.out.println("loading user from database");
//		User findByEmail = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
//		return findByEmail;
//	}


}