package com.sparta.outsideworld.controller;

import com.fasterxml.jackson.core.JacksonException;
import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.LoginRequestDto;
import com.sparta.outsideworld.dto.SignupRequestDto;
import com.sparta.outsideworld.entity.UserRoleEnum;
import com.sparta.outsideworld.jwt.JwtUtil;
import com.sparta.outsideworld.security.UserDetailsImpl;
import com.sparta.outsideworld.service.KakaoService;
import com.sparta.outsideworld.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

		// Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
		// cookie.setPath("/");
		// res.addCookie(cookie);
		jwtUtil.addJwtToCookie(token, res);
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

	@RequestMapping("/user/logout")
	public ResponseEntity<ApiResponseDto> logout(HttpServletResponse response, Authentication authResult) throws ServletException, IOException {
		log.info("로그아웃 시도");
		String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
		UserRoleEnum role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

		String token = jwtUtil.createToken(username, role);
		jwtUtil.deleteCookie(token, response);
		response.sendRedirect("/"); // "/"로 리다이렉트

		return ResponseEntity.status(201).body(new ApiResponseDto("로그아웃 성공", HttpStatus.CREATED.value()));
	}
}
