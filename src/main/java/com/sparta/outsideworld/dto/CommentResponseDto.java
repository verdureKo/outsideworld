package com.sparta.outsideworld.dto;

import com.sparta.outsideworld.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String username;
    private String image;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long likeCount;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.image = comment.getUser().getImage();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.likeCount = comment.getLikeCount();
    }
}
