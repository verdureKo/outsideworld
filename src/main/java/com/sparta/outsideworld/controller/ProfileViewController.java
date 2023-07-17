package com.sparta.outsideworld.controller;

import com.sparta.outsideworld.dto.ProfileResponseDto;
import com.sparta.outsideworld.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ProfileViewController {

    private final UserService userService;

    public ProfileViewController(UserService userService) {
        this.userService = userService;
    }

    // mypage 반환
    @GetMapping("/api/mypage")
    public String getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        ProfileResponseDto profileResponseDto = userService.getMyPage(userDetails.getUser());

        // model 필요한 데이터 담아서 반환
        model.addAttribute("users", profileResponseDto);
        model.addAttribute("posts", profileResponseDto.getPosts());
        return "mypage";
    }

    @GetMapping("/api/profile")
    public String getProfile() {
        return "mypageupdate";
    }

    @GetMapping("/api/profile/password")
    public String confirmPassword(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return "password";
    }

    @GetMapping("/api/profile/passwordupdate")
    public String getPassword(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return "passwordupdate";
    }
}