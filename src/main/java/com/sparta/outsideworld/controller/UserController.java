package com.sparta.outsideworld.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.sparta.outsideworld.dto.SignupRequestDto;
import com.sparta.outsideworld.dto.UserRequestDto;
import com.sparta.outsideworld.entity.KakaoProfile;
import com.sparta.outsideworld.entity.OAuthToken;
import com.sparta.outsideworld.entity.User;
import com.sparta.outsideworld.entity.UserRoleEnum;
import com.sparta.outsideworld.jwt.JwtUtil;
import com.sparta.outsideworld.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	public String kakaoCallback(String code) {

		// 코드값을 통해서 (로그인하려는 유저의 개인정보를 응답 받기 위해) AccessToken 을 부여 받음
		// AccessToken
		// Post 방식으로 Key=Value 데이터를 카카오톡으로 요청
		// Retrofit2 & OkHttp & RestTemplate = http 를 받는 방법

		RestTemplate rt = new RestTemplate();

		// HttpHeader 객체 생성
		HttpHeaders headers = new HttpHeaders();
		// Content-type 을 입력함으로 인해 이제부터 내가 대입할 데이터가 Key=Value 타입임을 명시
		headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 객체 생성
		// 기본 Map 이 한 key 당 하나의 value 를 저장시키는데 반해 MultiValueMap 은 한 key 당 여러 개의 value 를 저장시킬 수 있다.
		// HttpHeaders 는 MultiValueMap 을 상속한다.
		MultiValueMap <String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type","authorization_code");
		params.add("client_id","a980dc3fcc17a75986d402184c8ecc00");
		params.add("redirect_uri","http://localhost:8080/api/user/kakao/callback");
		params.add("code",code);

		// HttpHeader 와 HttpBody 를 하나의 객체에 담기(HttpEntity = HttpHeader 와 HttpBody 를 포함하는 클래스)
		// kakaoTokenRequest 는 header 값과 body 값을 갖고 있는 entity 가 됨
		HttpEntity <MultiValueMap <String, String>> kakaoTokenRequest =
			new HttpEntity<>(params, headers);

		// url: HTTP 요청이 전송되는 URL
		// method: 요청에 대한 HTTP 메서드
		// kakaoTokenRequest: 요청 본문과 헤더를 포함하는 HttpEntity 개체
		// String.class: 응답이 String 일 것으로 예상됨을 나타내는 응답 본문의 클래스
		ResponseEntity<String> response = rt.exchange(
			"https://kauth.kakao.com/oauth/token",
			HttpMethod.POST,
			kakaoTokenRequest,
			String.class

		);

		// Gson, Json Simple, ObjectMapper
		// JSON data 를 JAVA 언어로 컨버팅
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oAuthToken = null;

		try {
			oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		// 사용자 정보
		RestTemplate rt2 = new RestTemplate();

		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization","Bearer "+oAuthToken.getAccess_token());
		headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity <MultiValueMap <String, String>> kakaoProfileRequest =
			new HttpEntity<>(headers2);

		ResponseEntity<String> response2 = rt2.exchange(
			"https://kapi.kakao.com/v2/user/me",
			HttpMethod.POST,
			kakaoProfileRequest,
			String.class
		);

		// JSON data 를 JAVA 언어로 컨버팅
		ObjectMapper objectMapper2 = new ObjectMapper();
		objectMapper2.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		KakaoProfile kakaoProfile = null;

		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		// User 객체 : username, password, email 필요
		UUID tempPassword = UUID.randomUUID();

		User user = User.builder()
				.username(kakaoProfile.getKakaoAccount().getEmail()+"_"+kakaoProfile.getId())
			    .password(tempPassword.toString())
				.email(kakaoProfile.getKakaoAccount().getEmail())
				.build();

		userService.kakaoSignup(user);

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
}
