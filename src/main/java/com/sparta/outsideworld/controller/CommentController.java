package com.sparta.outsideworld.controller;

import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.CommentRequestDto;
import com.sparta.outsideworld.dto.CommentResponseDto;
import com.sparta.outsideworld.security.UserDetailsImpl;
import com.sparta.outsideworld.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comment/{postId}")
    public CommentResponseDto addComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.addComment(postId, requestDto, userDetails.getUser());
    }

    @PutMapping("/comment/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(commentId, requestDto, userDetails.getUser());
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(commentId, userDetails.getUser());
    }
}