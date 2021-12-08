package com.mycompany.webapp.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Member;

@Mapper
public interface MemberTestDao {
	public int insert(Member member);	
	public Member selectByMid(String mid);
}
