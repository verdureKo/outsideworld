package com.sparta.outsideworld.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "필수 입력 값입니다.")
    private String comment;
}
