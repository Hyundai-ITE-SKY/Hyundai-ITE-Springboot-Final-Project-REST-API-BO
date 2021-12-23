package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jmx.export.naming.IdentityNamingStrategy;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Event;
import com.mycompany.webapp.dto.Events;
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
	public Map<String, Object> getEventDetail(@RequestParam("eid") int eid) {
		log.info("실행");
		
		Map<String, Object> map = new HashMap<String, Object>();
		Event event = eventService.getEventDetail(eid);
		map.put("eid", event.getEid());
		map.put("ename", event.getEname());
		map.put("edetail", event.getEdetail());
		map.put("estartdate", event.getEstartdate());
		map.put("eenddate", event.getEenddate());
		map.put("eimage", event.getEimage());
		map.put("eamount", event.getEamount());
		map.put("elimit", event.getElimit());
		log.info("map"+map);
		return map;
	}
	
	@GetMapping("/totalrows")
	public Map<String, Object> getTotalRows(){
		Map<String, Object> map = new HashMap<>();

		map.put("value", eventService.getTotalEvent());
		
		return map;
	}
	
	@PostMapping("/create")
	public Map<String, Object> createEvent(Event event) {
		log.info("실행");
		Map<String, Object> map = new HashMap<>();
		
		map.put("value", eventService.createEvent(event));
		return map;
	}
	
	@PostMapping("/update")
	public Map<String, Object> updateEvent(Event event) {
		log.info("실행"); 
		Map<String, Object> map = new HashMap<>();
		map.put("value", eventService.updateEvent(event));
		return map;
	}
	
	@DeleteMapping("/delete")
	public Map<String, Object> deleteEvent(@RequestParam("eid") int eid) {
		log.info("실행");
		
		Map<String, Object> map = new HashMap<>();
		map.put("value", eventService.deleteEvent(eid));
		
		return map;
	}
	
	@PostMapping("/updateeorder")
	public Map<String, Object> updateEventOrder(@RequestBody Events events){
		log.info("실행"); 
		log.info("event"+events); 
		Map<String, Object> map = new HashMap<>();

		for(Event event : events.getEvents()) {
			eventService.updateEventOrder(event);
			map.put("value", eventService.updateEventOrder(event));
		}
		return map;
	}
}
