package com.sparta.outsideworld.controller;

import com.sparta.outsideworld.dto.SignupRequestDto;
import com.sparta.outsideworld.dto.UserRequestDto;
import com.sparta.outsideworld.jwt.JwtUtil;
import com.sparta.outsideworld.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

	private final UserService userService;
	private final JwtUtil jwtUtil;

	@GetMapping("/user/signup")
	public String signupPage() {
		return "signup";
	}

	@PostMapping("/user/signup")
	public String signup(@Valid @RequestBody SignupRequestDto userRequestDto){
		log.info("회원가입 시도");
		try {
			userService.signup(userRequestDto);
		} catch (IllegalArgumentException e) {
			return "signup";
		}
		return "redirect:/";
	}

	@GetMapping("/user/login")
	public String loginPage() {
		return "login";
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
}
