package com.in.event.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
// import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.in.event.constant.EventtexConstant;
import com.in.event.exceptions.EventtexCommonExceptions;
import com.in.event.model.User;
import com.in.event.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User userDetails = userRepository.getUserByName(email);
		System.out.println("printing fetched user: " + userDetails);
		System.out.println("user ka type :"+userDetails.getRole());
		if (userDetails == null) {
			throw new EventtexCommonExceptions(EventtexConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
		}

		if (userDetails != null) {
			return new org.springframework.security.core.userdetails.User(userDetails.getEmail(),
					userDetails.getPassword(), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("user not found !!");
		}
	}

}
