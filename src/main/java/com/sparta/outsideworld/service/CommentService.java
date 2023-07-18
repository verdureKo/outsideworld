package com.sparta.outsideworld.service;

import com.sparta.outsideworld.dto.ApiResponseDto;
import com.sparta.outsideworld.dto.CommentRequestDto;
import com.sparta.outsideworld.dto.CommentResponseDto;
import com.sparta.outsideworld.entity.*;
import com.sparta.outsideworld.repository.CommentRepository;
import com.sparta.outsideworld.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
//    private final LikeRepository likeRepository;
    private final MessageSource messageSource;

    @Transactional
    public CommentResponseDto addComment(Long postId, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException(messageSource.getMessage(
                        "not.exist.post",
                        null,
                        "해당 댓글이 존재하지 않습니다",
                        Locale.getDefault()
                ))
        );
        Comment comment = commentRepository.save(new Comment(requestDto, user, post));

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = findComment(commentId);
        if(!confirmUser(comment, user)){
            throw new IllegalArgumentException(messageSource.getMessage(
                    "not.your.post",
                    null,
                    "작성자만 수정이 가능합니다",
                    Locale.getDefault()
            ));
        }
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> deleteComment(Long commentId, User user) {
        Comment comment = findComment(commentId);

        if(!confirmUser(comment, user)){
            throw new IllegalArgumentException(messageSource.getMessage(
                    "not.your.post",
                    null,
                    "작성자만 삭제가 가능합니다",
                    Locale.getDefault()
            ));
        }

        commentRepository.delete(comment);

        ApiResponseDto apiResponseDto = new ApiResponseDto("삭제 완료", HttpStatus.OK.value());
        return new ResponseEntity<ApiResponseDto>(apiResponseDto, HttpStatus.OK);
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException(messageSource.getMessage(
                        "not.exist.comment",
                        null,
                        "해당 댓글이 존재하지 않습니다",
                        Locale.getDefault()
                ))
        );
    }

    private boolean confirmUser(Comment comment, User user) {
        UserRoleEnum userRoleEnum = user.getRole();
        return userRoleEnum != UserRoleEnum.USER || Objects.equals(comment.getUser().getId(), user.getId());
    }

}
