package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jmx.export.naming.IdentityNamingStrategy;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Event;
import com.mycompany.webapp.dto.OrderList;
import com.mycompany.webapp.dto.Pager;
import com.mycompany.webapp.service.EventService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/event")
@Slf4j
public class EventController {
	@Resource
	private EventService eventService;
	
	@GetMapping("/list")
	public Map<String, Object> getEvent(@RequestParam("pageNo") int pageNo) {
		log.info("실행");
		
		int totalRows = eventService.getTotalEvent();
		Pager pager = new Pager(5, 5, totalRows, pageNo);
		List<Event> eventLists = eventService.getEvent(pager);
		
		Map<String, Object> eventListMap = new HashMap<>();
		eventListMap.put("events", eventLists);
		
		return eventListMap;
	}
	
	@GetMapping("/detail")
	public Event getEventDetail(@RequestParam("eid") int eid) {
		log.info("실행");
		return eventService.getEventDetail(eid);
	}
	
	@GetMapping("/totalrows")
	public Map<String, Object> getTotalRows(){
		Map<String, Object> map = new HashMap<>();

		map.put("value", eventService.getTotalEvent());
		
		log.info(map+"");
		return map;
	}
	
	@PostMapping("/create")
	public Map<String, Object> createEvent(Event event) {
		log.info("실행");
		log.info("event API"+event);
		Map<String, Object> map = new HashMap<>();
		
		map.put("value", eventService.createEvent(event));
		return map;
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
