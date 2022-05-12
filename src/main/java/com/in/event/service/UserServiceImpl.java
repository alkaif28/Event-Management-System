package com.in.event.service;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.in.event.constant.EventtexConstant;
import com.in.event.exceptions.EventtexCommonExceptions;
import com.in.event.model.Event;
import com.in.event.model.User;
import com.in.event.model.UserEvent;
import com.in.event.repository.EventRepository;
import com.in.event.repository.UserEventRepository;
import com.in.event.repository.UserRepository;
import com.in.event.response.CommonResponse;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserEventRepository userEventRepository;

	final CommonResponse commonResponse = new CommonResponse();

	@Override
	public CommonResponse createUser(User user) {

		Base64.Encoder encoder = Base64.getEncoder();

		String encodedPassword = encoder.encodeToString(user.getPassword().getBytes());

		System.out.println("Encoded string: " + encodedPassword);
		user.setPassword(encodedPassword.toString());

		System.out.println("post man user : " + user.getEmail());
		// User userByName = userRepository.getUserByEmail(user.getEmail());
		Boolean existsByEmail = userRepository.existsByEmail(user.getEmail());
		System.out.println("edfsdhjsfsfsjkf   " + existsByEmail);
		if (existsByEmail) {
			throw new EventtexCommonExceptions(EventtexConstant.USER_ID_EXIST, HttpStatus.BAD_REQUEST);
		}
		user.setRole("USER");
		User saveUser = userRepository.save(user);
		commonResponse.setStatusCode(EventtexConstant.USER_RESPONSE_STATUS_OK);
		commonResponse.setMessage(EventtexConstant.USER_SAVE_SUCSESSFULLY);
		commonResponse.setData(saveUser);
		return commonResponse;
	}

	@Override
	public CommonResponse editUser(long userId, User user) {

		User UserIdExist = userRepository.findById(userId).orElse(null);
		System.out.println(UserIdExist);
		if (UserIdExist == null) {
			throw new EventtexCommonExceptions(EventtexConstant.INVALID_ID, HttpStatus.BAD_REQUEST);
		}
		UserIdExist.setUserName(user.getUserName());
		UserIdExist.setEmail(user.getEmail());
		UserIdExist.setPhoneNo(user.getPhoneNo());
		UserIdExist.setAddress(user.getAddress());
		User updatedDataUser = userRepository.save(UserIdExist);
		commonResponse.setStatusCode(EventtexConstant.USER_RESPONSE_STATUS_OK);
		commonResponse.setMessage(EventtexConstant.USER_UPDATED);
		commonResponse.setData(updatedDataUser);
		return commonResponse;
	}

	@Override
	public CommonResponse getAllUser() {
		List<User> findAllUser = userRepository.findAll();
		commonResponse.setStatusCode(EventtexConstant.USER_RESPONSE_STATUS_OK);
		commonResponse.setMessage(EventtexConstant.FIND_ALL_USER);
		commonResponse.setData(findAllUser);

		return commonResponse;
	}



	@Override
	public CommonResponse getAllEvent() {
		List<Event> getAllEvent = eventRepository.findAll();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(dtf.format(now));
		commonResponse.setStatusCode(EventtexConstant.USER_RESPONSE_STATUS_OK);
		commonResponse.setMessage(EventtexConstant.GET_ALL_EVENT);
		commonResponse.setData(getAllEvent);
		return commonResponse;
	}

	@Override
	public CommonResponse removeUserById(long userId) {

		userRepository.deleteById(userId);
		commonResponse.setStatusCode(EventtexConstant.USER_RESPONSE_STATUS_OK);
		commonResponse.setMessage(EventtexConstant.REMOVE_USER);

		return commonResponse;
	}

	@Override
	public CommonResponse EnrollUser(Long userId, Long eventId) {
		

		User userIdExist = userRepository.findById(userId).orElse(null);
		System.out.println("user all data :" + userIdExist);
		Event eventIdExist = eventRepository.findById(eventId).orElse(null);
		System.out.println("event all data :" + eventIdExist);
		UserEvent userEvent = new UserEvent();
		userEvent.setUser(userIdExist);
		userEvent.setEvent(eventIdExist);
		userEvent.setUserName(userIdExist.getUserName());
		userEvent.setEmail(userIdExist.getEmail());
		userEvent.setEventName(eventIdExist.getEventName());
		userEvent.setManagerName(eventIdExist.getManagerName());


		UserEvent save = userEventRepository.save(userEvent);

		System.out.println("save " + save);

		commonResponse.setStatusCode(EventtexConstant.USER_RESPONSE_STATUS_OK);
		commonResponse.setMessage(EventtexConstant.USER_ENROLL);
		commonResponse.setData("user enrolled");
		return commonResponse;
	}
	
	@Override
	public CommonResponse enrollDelete(long userEventId) {
		userEventRepository.deleteById(userEventId);
		commonResponse.setStatusCode(EventtexConstant.USER_RESPONSE_STATUS_OK);
		commonResponse.setMessage(EventtexConstant.REMOVE_USER);
		
		return commonResponse;

	}

	@Override
	public CommonResponse enrollUserList(Long userId) {
		
		List<UserEvent> getAllEnrollUser;
		if(userId != null) {
			getAllEnrollUser = userEventRepository.getByUserId(userId);
		} else {
			getAllEnrollUser = userEventRepository.findAll();
		}
		commonResponse.setStatusCode(EventtexConstant.USER_RESPONSE_STATUS_OK);
		commonResponse.setMessage(EventtexConstant.USER_ENROLL);
		commonResponse.setData(getAllEnrollUser);
		return commonResponse;
	}

}