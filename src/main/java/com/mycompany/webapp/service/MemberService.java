package com.mycompany.webapp.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mycompany.webapp.dao.memberdb.MemberDao;
import com.mycompany.webapp.dto.Grade;
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

	public List<Grade> getGradeList() {
		return memberDao.getGradeList();
	}

	@Transactional
	public String applyMemberGrade() {
		try {
			List<Member> members = getMemberList();

			for (Member member : members) {
				String grade = getGrade(member.getMtotalpayment());
				log.info(member.getMid() + ": " + grade);
				setGrade(member.getMid(), grade);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}

		return "success";
	}

	public String getGrade(int payment) {
		return memberDao.getGrade(payment);
	}

	public int setGrade(String mid, String grade) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("mid", mid);
		map.put("grade", grade);

		return memberDao.setGrade(map);
	}

	public int createMember(Member member) {
		return memberDao.createMember(member);
	}

	public int createGrade(Grade grade) {
		return memberDao.createGrade(grade);
	}

	public int updateGrade(int beforegmax, Grade grade) {
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("beforegmax", beforegmax);
		map.put("grade", grade);

		return memberDao.updateGrade(map);
	}

	public int deleteGrade(int gmax) {
		return memberDao.deleteGrade(gmax);
	}
}
