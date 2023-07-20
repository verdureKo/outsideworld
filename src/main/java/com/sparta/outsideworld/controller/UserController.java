package com.sparta.outsideworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JacksonException;
import com.sparta.outsideworld.dto.SignupRequestDto;
import com.sparta.outsideworld.dto.UserRequestDto;
import com.sparta.outsideworld.jwt.JwtUtil;
import com.sparta.outsideworld.service.KakaoService;
import com.sparta.outsideworld.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/api")
@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

	private final UserService userService;
	private final KakaoService kakaoService;
	private final JwtUtil jwtUtil;

	@GetMapping("/user/signup")
	public String signupPage() {
		return "signup";
	}

	@PostMapping("/user/signup")
	public String signup(@Valid SignupRequestDto userRequestDto){
		log.info("회원가입 시도");
		try {
			userService.signup(userRequestDto);
		} catch (IllegalArgumentException e) {
			return "signup";
		}
		return "redirect:/api/user/login";
	}

	@GetMapping("/user/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/user/kakao/callback")
	// @ResponseBody data 를 리턴해주는 컨트롤러 함수
	public String kakaoLogin(@RequestParam String code, HttpServletResponse res) throws JacksonException {

		String token = kakaoService.kakaoLogin(code);

		Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
		cookie.setPath("/");
		res.addCookie(cookie);
		return "redirect:/";
	}

	@PostMapping("/user/login")
	public String login (@RequestBody UserRequestDto loginRequestDto, HttpServletResponse response) {
		try {
			userService.login(loginRequestDto);
			response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername(),loginRequestDto.getRole()));
		} catch (IllegalArgumentException e) {
			return "login";
		}
		return "redirect:/";
	}

	// 비동기방식 로그아웃 메서드
	@RequestMapping(value = "logout.do", method = RequestMethod.POST)
	@ResponseBody
	public void logoutPost(HttpServletRequest request) throws Exception{
		log.info("비동기 로그아웃 메서드 진입");
		HttpSession session = request.getSession();
		session.invalidate();
	}
}
