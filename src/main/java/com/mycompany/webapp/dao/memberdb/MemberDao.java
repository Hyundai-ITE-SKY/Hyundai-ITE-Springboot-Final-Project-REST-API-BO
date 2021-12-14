package com.mycompany.webapp.dao.memberdb;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Grade;
import com.mycompany.webapp.dto.Event;
import com.mycompany.webapp.dto.Member;
import com.mycompany.webapp.dto.Pager;

@Mapper
public interface MemberDao {
	public List<Member> getMemberList();
	public Member selectByMid(String mid);
	public int deleteByMid(String mid);
	public int createMember(Member member);

	public List<Grade> getGradeList();
	public String getGrade(int payment);
	public int setGrade(HashMap<String, Object> map);
	public int createGrade(Grade grade);
	public int updateGrade(HashMap<String, Object> map);
	public int deleteGrade(int gmax);

	public List<Event> getEvent(Pager pager);
	public int createEvent(Event event);
	public Event getEventDetail(int eid);
	public int updateEvent(Event event);
	public int deleteEvent(int eid);
	public int getTotalEvent();
}