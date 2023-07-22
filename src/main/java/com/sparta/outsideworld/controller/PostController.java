package com.sparta.outsideworld.controller;

import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.PostRequestDto;
import com.sparta.outsideworld.dto.PostResponseDto;
import com.sparta.outsideworld.security.UserDetailsImpl;
import com.sparta.outsideworld.service.LikeService;
import com.sparta.outsideworld.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;
    private final LikeService likeService;

    // 게시글 전체 조회 API
    @GetMapping("/posts")
    public List<PostResponseDto> getPosts(){
        return postService.getPosts();
    }


    // 게시글 선택 조회 API
    @GetMapping("/post/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    // 게시글 등록 API
    @PostMapping("/post")
    @ResponseBody
    public ResponseEntity<ApiResponseDto> createPost(@RequestBody PostRequestDto postRequestDto,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info("title : " + postRequestDto.getTitle());
        log.info("contents : " + postRequestDto.getContents());

        postService.createPost(postRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("글 작성에 성공했습니다.", HttpStatus.CREATED.value()));
    }
    // 게시글 수정 API
    @PutMapping("/post/{postId}")
    public ResponseEntity<ApiResponseDto> updatePost(@PathVariable Long postId,
                                     @RequestBody PostRequestDto postRequestDto,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            postService.updatePost(postId, postRequestDto, userDetails.getUser());
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.ok().body(new ApiResponseDto("글 수정에 실패했습니다.", HttpStatus.BAD_REQUEST.value()));
        }
        return  ResponseEntity.ok().body(new ApiResponseDto("글 수정에 성공했습니다.", HttpStatus.OK.value()));
    }


    // 게시글 삭제 API
    @DeleteMapping("/post/{postId}")
    public ApiResponseDto deletePost(@PathVariable Long postId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(postId, userDetails.getUser());
    }

    //블로그 게시글 좋아요 API
    @PostMapping("/post/{postId}/like")
    public ResponseEntity<ApiResponseDto> likeBlog(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(likeService.likePost(postId, userDetails.getUser()));
    }

    //블로그 게시글 좋아요 취소 API
    @DeleteMapping("/post/{postId}/like")
    public ResponseEntity<ApiResponseDto> deleteLikeBlog(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(likeService.deleteLikePost(postId, userDetails.getUser()));
    }

}
