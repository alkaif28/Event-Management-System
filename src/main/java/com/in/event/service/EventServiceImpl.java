package com.in.event.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.in.event.constant.EventtexConstant;
import com.in.event.exceptions.EventtexCommonExceptions;
import com.in.event.model.Event;
import com.in.event.repository.EventRepository;
import com.in.event.response.CommonResponse;

@Service
public class EventServiceImpl implements IEventService {

	@Autowired
	private EventRepository eventRepository;

	final CommonResponse commonResponse = new CommonResponse();

	@Override
	public CommonResponse createEvent(Event event) {

		event.setStatus("upcoming");
		Event createEvent = eventRepository.save(event);
		commonResponse.setStatusCode(EventtexConstant.EVENT_STATUS_SUCCSESS);
		commonResponse.setMessage(EventtexConstant.EVENT_CREATE);
		commonResponse.setData(createEvent);
		return commonResponse;
	}

	@Override
	public CommonResponse editEvent(Event event, long eventId) {
		Optional<Event> findById = eventRepository.findById(eventId);
		if (!findById.isPresent()) {
			throw new EventtexCommonExceptions(EventtexConstant.EVENT_ID_NOT_FOUND, HttpStatus.BAD_REQUEST);
		}
		
		event.setEventId(eventId);
		event.setStatus("upcoming");
		event.setAddress(event.getAddress());
		event.setDate(event.getDate());
		event.setEventName(event.getEventName());
		event.setManagerName(event.getManagerName());
		event.setManagerPhone(event.getManagerPhone());
		Event editEvent = eventRepository.save(event);
		commonResponse.setStatusCode(EventtexConstant.EVENT_STATUS_SUCCSESS);
		commonResponse.setMessage(EventtexConstant.EVENT_UPDATE);
		commonResponse.setData(editEvent);
		return commonResponse;
	}

	@Override
	public CommonResponse getEventById(long eventId) {
		Optional<Event> findById = eventRepository.findById(eventId);
		if (findById.isEmpty()) {
			throw new EventtexCommonExceptions(EventtexConstant.INVALID_EVENT_ID, HttpStatus.BAD_REQUEST);
		}
		commonResponse.setStatusCode(EventtexConstant.EVENT_STATUS_SUCCSESS);
		commonResponse.setMessage(EventtexConstant.FIND_EVENT_BY_ID);
		commonResponse.setData(findById);
		return commonResponse;
	}

	@Override
	public CommonResponse removeEventById(long eventId) {
		eventRepository.deleteById(eventId);
		commonResponse.setStatusCode(EventtexConstant.EVENT_STATUS_SUCCSESS);
		commonResponse.setMessage(EventtexConstant.REMOVE_Event);

		return commonResponse;
	}

}
