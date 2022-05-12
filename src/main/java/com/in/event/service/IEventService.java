package com.in.event.service;

import com.in.event.model.Event;
import com.in.event.response.CommonResponse;

public interface IEventService {

	CommonResponse createEvent(Event event);

	CommonResponse editEvent(Event event, long eventId);

	CommonResponse getEventById(long eventId);

	CommonResponse removeEventById(long eventId);

	

}
