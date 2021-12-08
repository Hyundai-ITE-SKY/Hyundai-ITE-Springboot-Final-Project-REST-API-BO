package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.memberdb.MemberDao;
import com.mycompany.webapp.dto.Event;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventService {
	@Resource
	private MemberDao memberDao;
	
	public List<Event> getEvent() {
		log.info("실행");
		return memberDao.getEvent();
	}
	
	public int createEvent(Event event) {
		log.info("실행");
		return memberDao.createEvent(event);
	}

	public Event getEventDetail(int eid) {
		log.info("실행");
		return memberDao.getEventDetail(eid);
	}
	
	public int updateEvent(Event event) {
		log.info("실행");
		return memberDao.updateEvent(event);
	}
	
	public int deleteEvent(int eid) {
		log.info("실행");
		return memberDao.deleteEvent(eid);
	}
}
