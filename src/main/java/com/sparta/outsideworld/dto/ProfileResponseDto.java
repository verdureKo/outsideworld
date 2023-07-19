package com.sparta.outsideworld.dto;

import com.sparta.outsideworld.entity.User;
import lombok.Getter;

import java.util.List;

@Getter
public class ProfileResponseDto {
    private String username;
    private String email;
    private String introduction;
    private String image;
    private List<ProfilePostListResponseDto> posts;

    public ProfileResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.introduction = user.getIntroduction();
        this.image = user.getImage();
//        this.posts = user.getPostList().stream().map(ProfilePostListResponseDto::new).sorted(Comparator.comparing(ProfilePostListResponseDto::getCreatedAt).reversed()).toList();
    }
}
