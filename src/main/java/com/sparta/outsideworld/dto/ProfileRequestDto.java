package com.sparta.outsideworld.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProfileRequestDto {
    @NotBlank(message = "필수 입력 값입니다.")
    private String email;
    @NotBlank(message = "필수 입력 값입니다.")
    private String introduction;
    private String image;
}