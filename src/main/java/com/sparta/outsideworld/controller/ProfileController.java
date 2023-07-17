package com.sparta.outsideworld.controller;

import com.sparta.outsideworld.dto.PasswordRequestDto;
import com.sparta.outsideworld.dto.ProfileRequestDto;
import com.sparta.outsideworld.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.concurrent.RejectedExecutionException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    // 프로필 수정
    @Transactional
    @PutMapping("/api/profile")
    public ResponseEntity<String> updateProfile(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ProfileRequestDto profileRequestDto) {
        log.info("프로필 수정 시도");
        userService.updateProfile(userDetails.getUser(), profileRequestDto);
        log.info("프로필 수정 완료 후에 상태값 확인");
        URI redirectUri = URI.create("/api/mypage"); // 리다이렉션할 URL
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(redirectUri)
                .build();
    }

    // 비밀번호 확인
    @PostMapping("/api/profile/password")
    public ResponseEntity<String> checkPassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody PasswordRequestDto passwordRequestDto) {
        log.info("비밀번호 일치 여부 확인");
        return userService.confirmPassword(userDetails, passwordRequestDto);
    }

    // 비밀번호 변경
    @Transactional
    @PutMapping("/api/profile/passwordupdate")
    public ResponseEntity<String> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody PasswordRequestDto passwordRequestDto) {
        return userService.updatePassword(userDetails, passwordRequestDto);
    }
}