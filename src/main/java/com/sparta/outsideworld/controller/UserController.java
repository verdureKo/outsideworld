package com.sparta.outsideworld.controller;

import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.LoginRequestDto;
import com.sparta.outsideworld.dto.SignupRequestDto;
import com.sparta.outsideworld.dto.UserRequestDto;
import com.sparta.outsideworld.jwt.JwtUtil;
import com.sparta.outsideworld.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@ResponseBody
	@PostMapping("/user/login")
	public ResponseEntity<ApiResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
		log.info("login 시도");
		try {
			userService.login(loginRequestDto);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.ok().body(new ApiResponseDto("로그인에 실패했습니다.", HttpStatus.BAD_REQUEST.value()));
		}

		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername(),loginRequestDto.getRole()));
		jwtUtil.addJwtToCookie(response.getHeader(JwtUtil.AUTHORIZATION_HEADER), response);

		log.info(response.getHeader(JwtUtil.AUTHORIZATION_HEADER));
		return ResponseEntity.ok().body(new ApiResponseDto("로그인에 성공했습니다.", HttpStatus.OK.value()));
	}
}
