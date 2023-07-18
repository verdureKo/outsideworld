package com.sparta.outsideworld.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.UserRequestDto;
import com.sparta.outsideworld.jwt.JwtUtil;
import com.sparta.outsideworld.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;
	private final JwtUtil jwtUtil;

	@PostMapping("/user/signup")
	public ResponseEntity <ApiResponseDto> signup (@Valid @RequestBody UserRequestDto userRequestDto){
		try {
			userService.signup(userRequestDto);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
		}
		return ResponseEntity.ok().body(new ApiResponseDto("회원가입이 성공했습니다.", HttpStatus.OK.value()));
	}

	@PostMapping("/user/login")
	public ResponseEntity <ApiResponseDto> login (@RequestBody UserRequestDto loginRequestDto, HttpServletResponse response) {
		try {
			userService.login(loginRequestDto);
			response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername(),loginRequestDto.getRole()));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
		}
		return ResponseEntity.ok().body(new ApiResponseDto("로그인에 성공했습니다.", HttpStatus.OK.value()));
	}
}
