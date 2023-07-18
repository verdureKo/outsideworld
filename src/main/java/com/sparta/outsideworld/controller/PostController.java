package com.sparta.outsideworld.controller;

import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.PostRequestDto;
import com.sparta.outsideworld.dto.PostResponseDto;
import com.sparta.outsideworld.security.UserDetailsImpl;
import com.sparta.outsideworld.service.LikeService;
import com.sparta.outsideworld.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    private final LikeService likeService;

    // 게시글 전체 조회 API
//    @GetMapping("/posts")
//    public PostResponseDto getPosts(){
//        return postService.getPosts();
//    }


    // 게시글 선택 조회 API
    @GetMapping("/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }


    // 게시글 등록 API
    @PostMapping("/post")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.createPost(postRequestDto, userDetails.getUser());
    }


//    // 게시글 수정 API
//    @PutMapping("/post/{id}")
//    public PostResponseDto updatePost(@PathVariable Long id,
//                                     @RequestBody PostRequestDto postRequestDto,
//                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return postService.updatePost(id, postRequestDto, userDetails);
//    }


    // 게시글 삭제 API
//    @DeleteMapping("/post/{id}")
//    public PostResponseDto deletePost(@PathVariable Long id,
//                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return postService.deletePost(id, userDetails);
//    }

    //블로그 게시글 좋아요 API
    @PostMapping("/post/{id}/like")
    public ResponseEntity<ApiResponseDto> likeBlog(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(likeService.likePost(id, userDetails.getUser()));
    }

    //블로그 게시글 좋아요 취소 API
    @DeleteMapping("/post/{id}/like")
    public ResponseEntity<ApiResponseDto> deleteLikeBlog(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(likeService.deleteLikePost(id, userDetails.getUser()));
    }


}
