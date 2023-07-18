package com.sparta.outsideworld.dto;

import com.sparta.outsideworld.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String username;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
//    private int likeCount;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedTime();
        this.modifiedAt = comment.getUpdatedTime();
//        this.likeCount = comment.getLikeCount();
    }
}
