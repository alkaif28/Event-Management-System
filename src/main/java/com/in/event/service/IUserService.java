package com.in.event.service;




import com.in.event.model.User;
import com.in.event.response.CommonResponse;

public interface IUserService {

	public CommonResponse createUser(User user);

	public CommonResponse editUser(long userId, User user);

	public CommonResponse getAllUser();

	public CommonResponse getAllEvent();

	public CommonResponse removeUserById(long userId);

	public CommonResponse EnrollUser(Long userId ,Long eventId);
	
	public CommonResponse enrollDelete(long userEventId);

	public CommonResponse enrollUserList(Long userId);

}
