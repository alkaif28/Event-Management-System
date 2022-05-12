package com.in.event.controller;


import java.util.Base64;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in.event.constant.EventtexConstant;
import com.in.event.exceptions.EventtexCommonExceptions;
import com.in.event.model.ResponseToken;
import com.in.event.model.User;
import com.in.event.repository.UserRepository;
import com.in.event.response.CommonResponse;
import com.in.event.service.IUserService;
import com.in.event.service.JwtUserDetailsService;
import com.in.event.utility.JwtUtils;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:4200")
public class UserController {

	@Autowired
	private IUserService iUserService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping("/createUser")
	public ResponseEntity<CommonResponse> createUser(@RequestBody User user) {

		return new ResponseEntity<CommonResponse>(iUserService.createUser(user), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseToken loginUser(@RequestBody User user) {
		if (user == null) {
			throw new EventtexCommonExceptions(EventtexConstant.INVALID_DATA, HttpStatus.BAD_REQUEST);
		}
		
		  Base64.Encoder encoder = Base64.getEncoder();
		  System.out.println("user login object " + user);
		  
		  String pwd = encoder.encodeToString(user.getPassword().getBytes());
		  
		  System.out.println("password   =====" +pwd);
		  
		  Base64.Decoder decoder = Base64.getDecoder();  
		  
		  String stringDecode = new String(decoder.decode(pwd));
		  System.out.println("decoded password === "+stringDecode);
		  
		 

		try {
			System.out.println("try start");
			this.authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), pwd));
			System.out.println("try end");
		} catch (Exception e) {
			throw new UsernameNotFoundException("wrong credential");
		}

		UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(user.getEmail());

		User userDetails1 = userRepository.getUserByName(user.getEmail());

		System.out.println("at end");
		return new ResponseToken(this.jwtUtils.generateToken(userDetails), userDetails1.getRole(),
				userDetails1.getUserName(), userDetails1.getUserId());

	}

	@PutMapping("/editUser/{userId}")
	public ResponseEntity<CommonResponse> editUser(@PathVariable("userId") long userId, @RequestBody User user) {
		return new ResponseEntity<CommonResponse>(iUserService.editUser(userId, user), HttpStatus.OK);
	}

	@GetMapping("/getAllUser")
	public ResponseEntity<CommonResponse> getAllUser() {
		return new ResponseEntity<CommonResponse>(iUserService.getAllUser(), HttpStatus.OK);
	}

	@GetMapping("/getAllEvent")
	public ResponseEntity<CommonResponse> getAllEvent() {
		return new ResponseEntity<CommonResponse>(iUserService.getAllEvent(), HttpStatus.OK);
	}

	@DeleteMapping("/removeUser/{id}")
	public ResponseEntity<CommonResponse> removeUser(@PathVariable("id") long userId) {
		return new ResponseEntity<CommonResponse>(iUserService.removeUserById(userId), HttpStatus.OK);
	}

	@GetMapping("/userEnroll")
	public ResponseEntity<CommonResponse> userAndEventEnroll(@RequestParam("userId") Long userId, @RequestParam("eventId") Long evenId) {
	System.out.println("usrEnroll=");
		return new ResponseEntity<CommonResponse>(iUserService.EnrollUser(userId,evenId), HttpStatus.OK);
	}
	
	@DeleteMapping("/removeEnrollUser/{userEventId}")
	public ResponseEntity<CommonResponse> EventEnrollDelete(@PathVariable long  userEventId) {
		System.out.println("usrEnroll="+userEventId);
	return new ResponseEntity<CommonResponse>(iUserService.enrollDelete(userEventId),HttpStatus.OK);
	}
	
	@GetMapping("/enrollUserList")
	public ResponseEntity<CommonResponse> enrollUserList(@RequestParam(name="userId", required=false) Long userId) {
		return new ResponseEntity<CommonResponse>(iUserService.enrollUserList(userId),HttpStatus.OK);
	}
	
}
