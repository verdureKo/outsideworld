package com.sparta.outsideworld.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
public class PostRequestDto {

    @NotBlank(message = "필수 입력 값입니다.")
    private String title; // 게시글 제목
    @NotBlank(message = "필수 입력 값입니다.")
    private String contents; // 게시글 내용

    private String image;	// 프론트에서 default 이미지 부탁합니다
}
