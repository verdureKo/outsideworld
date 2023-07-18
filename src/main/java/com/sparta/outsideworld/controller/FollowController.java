package com.sparta.outsideworld.controller;

import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.PostResponseDto;
import com.sparta.outsideworld.security.UserDetailsImpl;
import com.sparta.outsideworld.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;

    // 팔로우 기능 API
    @PostMapping("/follow/{id}")
    public ApiResponseDto followUser(@PathVariable Long id,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        return followService.followUser(id, userDetails.getUser().getId());
    }

    // 언팔로우 기능 API
    @DeleteMapping("/follow/{id}")
    public ApiResponseDto unfollowUser(@PathVariable Long id,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails){
        return followService.unfollowUser(id, userDetails.getUser().getId());
    }

    // 팔로우 한 유저의 전체게시글 조회 API
    @GetMapping("/follow/posts")
    public List<PostResponseDto> getFollowPosts(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return followService.getFollowPosts(userDetails.getUser().getId());
    }

    // 팔로우 한 특정 유저의 전체게시글 조회 API
//    @GetMapping("/follow/post/{id}")
//    public List<PostResponseDto> getFollowPost(@PathVariable Long id,
//                                               @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return followService.getFollowPost(id, userDetails.getUser().getId());
//    }

}
