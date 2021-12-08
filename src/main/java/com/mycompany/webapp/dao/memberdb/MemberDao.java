package com.mycompany.webapp.dao.memberdb;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.webapp.dto.Member;

@Mapper
public interface MemberDao {
	public List<Member> getMemberList();
	public Member selectByMid(String mid);
}