package com.mycompany.webapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.memberdb.MemberDao;
import com.mycompany.webapp.dto.Member;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService {

	@Resource
	private MemberDao memberDao;

	public List<Member> getMemberList() {
		log.info("실행");
		return memberDao.getMemberList();
	}
	
	public Member selectByMid(String mid) {
		return memberDao.selectByMid(mid);
	}
}
