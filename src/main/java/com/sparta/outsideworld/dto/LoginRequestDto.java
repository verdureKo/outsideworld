package com.sparta.outsideworld.dto;

import com.sparta.outsideworld.entity.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank(message = "필수 입력 값입니다.")
    private String username;

    @NotBlank(message = "필수 입력 값입니다.")
    private String password;

    private UserRoleEnum role; // 회원 권한 (ADMIN, USER)
}
