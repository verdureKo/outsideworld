package com.sparta.outsideworld.dto;

import com.sparta.outsideworld.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProfilePostListResponseDto {
    private String username;
    private String title;
    private String image;
    private LocalDateTime createdAt;

    public ProfilePostListResponseDto(Post post) {
        this.username = post.getUser().getUsername();
        this.title = post.getTitle();
        this.image = post.getUser().getImage();
        this.createdAt = post.getCreatedAt();
    }
}
