package com.mycompany.webapp.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.memberdb.MemberDao;
import com.mycompany.webapp.dto.Member;

@Service
public class AdminService {
	
	@Resource
	private MemberDao memberDao;

	public Member getAdminInfo(String mid) {
		return memberDao.selectByMid(mid);
	}
	
	
}
