package com.sparta.outsideworld.service;

import com.sparta.outsideworld.dto.*;
import com.sparta.outsideworld.entity.User;
import com.sparta.outsideworld.entity.UserRoleEnum;
import com.sparta.outsideworld.repository.UserRepository;
import com.sparta.outsideworld.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // signup
    // ADMIN_TOKEN -- 일반 사용자와 관리자 권한 사용자 구분
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequestDto userRequestDto){
        String username = userRequestDto.getUsername();
        String password = passwordEncoder.encode(userRequestDto.getPassword());
        String email = userRequestDto.getEmail();
        String introduction = userRequestDto.getIntroduction();
        String image = userRequestDto.getImage();

        // 가입된 정보와 신규 회원 중복 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 Username 입니다.");
        } else if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (userRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(userRequestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 정보 DB 에 저장
        User user = new User(username, password, email, introduction, image, role);
        userRepository.save(user);
    }

    @Transactional
    public void login(UserRequestDto loginRequestDto){
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new IllegalArgumentException("등록된 사용자가 없습니다"));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

    }

    public ProfileResponseDto getMyPage(User user) {
        return new ProfileResponseDto(user);
    }

    // 프로필 수정
    @Transactional
    public void updateProfile(User user, ProfileRequestDto profileRequestDto) {
        log.info("회원정보 수정");
        user.setEmail(profileRequestDto.getEmail());
        user.setIntroduction(profileRequestDto.getIntroduction());
        user.setImage(profileRequestDto.getImage());
        log.info("회원정보 수정 시도");
        userRepository.save(user);
        log.info("회원정보 수정 완료");
    }

    @Transactional
    public ResponseEntity <String> updatePassword(UserDetailsImpl userDetails, PasswordRequestDto passwordRequestDto) {
        User user = userDetails.getUser();
        if (passwordEncoder.matches(passwordRequestDto.getPassword(), userDetails.getPassword()) || passwordEncoder.matches(passwordRequestDto.getPassword(), user.getOldPassword1()) || passwordEncoder.matches(passwordRequestDto.getPassword(), user.getOldPassword2())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("최근 3회 이내로 사용했던 비밀번호입니다.");
        } else {
            String password = passwordEncoder.encode(passwordRequestDto.getPassword());
            user.setOldPassword2(user.getOldPassword1());
            user.setOldPassword1(userDetails.getPassword());
            user.setPassword(password);
            userRepository.save(user);
            return ResponseEntity.ok().body("Success");
        }
    }

    // 비밀번호 확인
    public ResponseEntity <String> confirmPassword(UserDetailsImpl userDetails, PasswordRequestDto passwordRequestDto) {
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
