package com.in.event.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private long eventId;
	private String eventName;
	private String date;
	private String address;
	private String managerName;
	private long managerPhone;
	private String status;

}
