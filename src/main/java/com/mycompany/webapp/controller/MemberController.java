package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.dto.Grade;
import com.mycompany.webapp.dto.Member;
import com.mycompany.webapp.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/member")
@Slf4j
public class MemberController {

	@Resource
	private MemberService memberService;

	@GetMapping("/list")
	public Map<String, Object> getMemberList(HttpServletRequest request) {
		log.info("실행");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("members", memberService.getMemberList());

		return map;
	}

	@PostMapping("/create")
	public Map<String, Object> createMember(Member member, HttpServletRequest request) {
		log.info("실행");

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			member.setMpassword("{noop}" + member.getMpassword());
			memberService.createMember(member);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "fail");
			return map;
		}

		map.put("result", "success");
		return map;
	}

	@RequestMapping("/detail")
	public Member getMemberDetail(String mid, HttpServletRequest request) {
		log.info("실행");

		return memberService.selectByMid(mid);
	}

	@RequestMapping("/delete")
	public Map<String, Object> deleteMember(String mid, HttpServletRequest request) {
		log.info("실행");

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			memberService.deleteByMid(mid);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "fail");
			return map;
		}

		map.put("result", "success");
		return map;
	}

	@GetMapping("/grade/list")
	public Map<String, Object> getGradeList(HttpServletRequest request) {
		log.info("실행");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("grades", memberService.getGradeList());

		return map;
	}

	@GetMapping("/grade/apply")
	public Map<String, Object> applyGrade(HttpServletRequest request) {
		log.info("실행");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", memberService.applyMemberGrade());

		return map;
	}

	@PostMapping("/grade/create")
	public Map<String, Object> createGrade(Grade grade, HttpServletRequest request) {
		log.info("실행");

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			memberService.createGrade(grade);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "fail");
			return map;
		}

		map.put("result", "success");
		return map;
	}

	@RequestMapping("/grade/update/{beforegmax}")
	public Map<String, Object> updateGrade(@PathVariable int beforegmax, Grade grade, HttpServletRequest request) {
		log.info("실행");

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			memberService.updateGrade(beforegmax, grade);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "fail");
			return map;
		}

		map.put("result", "success");
		return map;
	}

	@DeleteMapping("/grade/delete")
	public Map<String, Object> deleteGrade(int gmax, HttpServletRequest request) {
		log.info("실행");

		Map<String, Object> map = new HashMap<String, Object>();

		try {
			memberService.deleteGrade(gmax);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "fail");
			return map;
		}

		map.put("result", "success");
		return map;
	}
}
