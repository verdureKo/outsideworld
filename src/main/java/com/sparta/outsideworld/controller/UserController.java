package com.sparta.outsideworld.controller;

import com.fasterxml.jackson.core.JacksonException;
import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.LoginRequestDto;
import com.sparta.outsideworld.dto.SignupRequestDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/user/logout")
	public void logout(HttpServletResponse response) {
		//원래 쿠키의 이름이 userInfo 이었다면, value를 null로 처리.
		Cookie myCookie = new Cookie("Authorization", null);
		myCookie.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
		myCookie.setPath("/"); // 모든 경로에서 삭제 됬음을 알린다.
		response.addCookie(myCookie);
	}
}
