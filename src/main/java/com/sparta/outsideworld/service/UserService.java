package com.sparta.outsideworld.service;

import com.sparta.outsideworld.dto.PasswordRequestDto;
import com.sparta.outsideworld.dto.ProfileRequestDto;
import com.sparta.outsideworld.dto.ProfileResponseDto;
import com.sparta.outsideworld.entity.User;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    public ProfileResponseDto getMyPage(User user) {
        return new ProfileResponseDto(user);
    }

    // 프로필 수정
    @Transactional
    public void updateProfile(User user, ProfileRequestDto profileRequestDto) {
        log.info("회원정보 수정");
        user.setEmail(profileRequestDto.getEmail());
        user.setIntroduction(profileRequestDto.getIntroduction());
        log.info("회원정보 수정 시도");
        userRepository.save(user);
        log.info("회원정보 수정 완료");
    }

    @Transactional
    public ResponseEntity<String> updatePassword(UserDetailsImpl userDetails, PasswordRequestDto passwordRequestDto) {
        User user = userDetails.getUser();
        String password = passwordEncoder.encode(passwordRequestDto.getPassword());
        if (password.equals(user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("최근 3회 이내로 사용했던 비밀번호입니다.");
        } else {
            user.setPassword(password);
            userRepository.save(user);
            return ResponseEntity.ok().body("Success");
        }
    }

    // 비밀번호 확인
    public ResponseEntity<String> confirmPassword(UserDetailsImpl userDetails, PasswordRequestDto passwordRequestDto) {
        log.info(userDetails.getPassword());
        log.info(passwordRequestDto.getPassword());

        if (passwordEncoder.matches(passwordRequestDto.getPassword(), userDetails.getPassword())) {
            log.info("성공");
            return ResponseEntity.ok("Success");
        } else {
            log.info("실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error"); // 상태 코드 400 반환
        }
    }
}
