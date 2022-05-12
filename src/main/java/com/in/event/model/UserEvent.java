package com.in.event.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long userEventId;

	private String userName;
	private String email;
	private String eventName;
	private String managerName;

	@Transient
	private long userId;
	@Transient
	private long eventId;
	
	@ManyToOne( fetch = FetchType.EAGER)
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Event event;
}
