package com.sparta.outsideworld.controller;

import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.CommentRequestDto;
import com.sparta.outsideworld.dto.CommentResponseDto;
import com.sparta.outsideworld.security.UserDetailsImpl;
import com.sparta.outsideworld.service.CommentService;
import com.sparta.outsideworld.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final LikeService likeService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<ApiResponseDto> addComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            commentService.addComment(postId, requestDto, userDetails.getUser());
        } catch (NullPointerException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().body(new ApiResponseDto("댓글 작성에 성공했습니다.", HttpStatus.CREATED.value()));
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        log.info(requestDto.getComment());
        try {
            commentService.updateComment(commentId, requestDto, userDetails.getUser());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok().body(new ApiResponseDto("댓글 수정에 실패했습니다", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.ok().body(new ApiResponseDto("댓글을 수정했습니다.", HttpStatus.OK.value()));
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        ResponseEntity<ApiResponseDto> response;
        try {
            response = commentService.deleteComment(commentId, userDetails.getUser());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제에 실패했습니다", HttpStatus.BAD_REQUEST.value()));
        }

        return response;
    }

    // 댓글 좋아요 API
    @PostMapping("/post/{postId}/comment/{commentId}/like")
    public ResponseEntity<ApiResponseDto> likeComment(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(likeService.likeComment(postId, commentId, userDetails.getUser()));
    }

    // 댓글 좋아요 취소 API
    @DeleteMapping("/post/{postId}/comment/{commentId}/like")
    public ResponseEntity<ApiResponseDto> deleteLikeComment(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(likeService.deleteLikeComment(postId, commentId, userDetails.getUser()));
    }
}