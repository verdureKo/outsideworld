package com.sparta.outsideworld.dto;

import com.sparta.outsideworld.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProfilePostListResponseDto {
    private String username;
    private String title;
    private LocalDateTime createdAt;

    public ProfilePostListResponseDto(Post post) {
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.createdAt = post.getCreatedAt();
    }
}
