package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public List<Member> getMemberList(HttpServletRequest request) {
		log.info("실행");
		return memberService.getMemberList();
	}
	
	@GetMapping("/grade/list")
	public Map<String, Object> getGradeList(HttpServletRequest request) {
		log.info("실행");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("grades", memberService.getGradeList());
		
		return map;
	}
}
