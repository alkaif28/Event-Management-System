package com.in.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.event.model.Event;
import com.in.event.response.CommonResponse;
import com.in.event.service.IEventService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("http://localhost:4200")
public class EventController {

	@Autowired
	private IEventService iEventService;

	@PostMapping("/createEvent")
	public ResponseEntity<CommonResponse> createEvent(@RequestBody Event event) {
		return new ResponseEntity<CommonResponse>(iEventService.createEvent(event), HttpStatus.OK);
	}

	@PutMapping("/editEvent/{id}")
	public ResponseEntity<CommonResponse> editEvent(@RequestBody Event event, @PathVariable("id") long eventId) {
		return new ResponseEntity<CommonResponse>(iEventService.editEvent(event, eventId), HttpStatus.OK);
	}
	
	@GetMapping("/getEventById/{id}")
	public ResponseEntity<CommonResponse> getEventById(@PathVariable("id") long eventId){
		return new ResponseEntity<CommonResponse>(iEventService.getEventById(eventId), HttpStatus.OK);

	}
	
	@DeleteMapping("/removeEvent/{id}")
	public ResponseEntity<CommonResponse> removeEventById(@PathVariable("id") long eventId){
		return  new ResponseEntity<CommonResponse>(iEventService.removeEventById(eventId), HttpStatus.OK);
		
	}
	
	
	
}
