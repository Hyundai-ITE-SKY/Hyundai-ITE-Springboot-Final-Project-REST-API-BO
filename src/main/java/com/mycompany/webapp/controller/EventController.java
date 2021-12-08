package com.mycompany.webapp.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Event;
import com.mycompany.webapp.service.EventService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/event")
@Slf4j
public class EventController {
	@Resource
	private EventService eventService;
	
	@GetMapping("/list")
	public List<Event> getEvent() {
		log.info("실행");
		return eventService.getEvent();
	}
	
	@GetMapping("/detail")
	public Event getEventDetail(int eid) {
		log.info("실행");
		return eventService.getEventDetail(eid);
	}
	
	@PostMapping("/create")
	public int createEvent(Event event) {
		log.info("실행");
		return eventService.createEvent(event);
	}
	
	@PostMapping("/update")
	public int updateEvent(Event event) {
		log.info("실행");
		return eventService.updateEvent(event);
	}
	
	@DeleteMapping("/delete")
	public int deleteEvent(int eid) {
		log.info("실행");
		return eventService.deleteEvent(eid);
	}
}
