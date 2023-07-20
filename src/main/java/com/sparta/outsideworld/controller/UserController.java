package com.sparta.outsideworld.controller;

import com.fasterxml.jackson.core.JacksonException;
import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.LoginRequestDto;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.LoginRequestDto;
import com.sparta.outsideworld.dto.SignupRequestDto;
import com.sparta.outsideworld.dto.UserRequestDto;
import com.sparta.outsideworld.entity.KakaoProfile;
import com.sparta.outsideworld.entity.OAuthToken;
import com.sparta.outsideworld.entity.User;
import com.sparta.outsideworld.entity.UserRoleEnum;
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
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

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

	@ResponseBody
	@PostMapping("/user/signup")
	public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto userRequestDto){
		log.info("회원가입 시도");
		try {
			userService.signup(userRequestDto);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.ok().body(new ApiResponseDto("회원가입에 실패했습니다.", HttpStatus.BAD_REQUEST.value()));
		}
		return ResponseEntity.ok().body(new ApiResponseDto("회원가입에 성공했습니다.", HttpStatus.CREATED.value()));
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

	@ResponseBody
	@PostMapping("/user/login")
	public ResponseEntity<ApiResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
		log.info("login 시도");
		try {
			userService.login(loginRequestDto);
			response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername(),loginRequestDto.getRole()));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.ok().body(new ApiResponseDto("로그인에 실패했습니다.", HttpStatus.BAD_REQUEST.value()));
		}

		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername(),loginRequestDto.getRole()));
		jwtUtil.addJwtToCookie(response.getHeader(JwtUtil.AUTHORIZATION_HEADER), response);

		log.info(response.getHeader(JwtUtil.AUTHORIZATION_HEADER));
		return ResponseEntity.ok().body(new ApiResponseDto("로그인에 성공했습니다.", HttpStatus.OK.value()));
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
