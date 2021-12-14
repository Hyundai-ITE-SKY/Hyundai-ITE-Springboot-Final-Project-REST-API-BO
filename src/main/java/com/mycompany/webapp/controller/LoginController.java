package com.mycompany.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.webapp.security.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LoginController {

	@Resource
	private AuthenticationManager authenticationManager;

	@RequestMapping("/login")
	public Map<String, String> login(String mid, String mpassword) {
		log.info("실행");
		Map<String, String> map = new HashMap<>();

		try {
			if (mid == null || mpassword == null) {
				throw new BadCredentialsException("아이디 또는 패스워드가 제공되지 않았음");
			}

			// Spring Security 사용자 인증
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(mid, mpassword);

			// mid와 mpassword가 db의 정보와 맞냐 안맞냐를 검증함
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(auth);

			// 응답 내용
			// mid에 해당하는 권한을 어떻게 얻느냐
			String authority = auth.getAuthorities().iterator().next().toString();

			log.info(authority);

			map.put("result", "success");
			map.put("mid", mid);
			map.put("jwt", JwtUtil.createToken(mid, authority));
			map.put("authority", authority);
		} catch (Exception e) {
			map.put("result", "fail");
		}

		return map;
	}
}