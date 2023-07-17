package com.sparta.outsideworld.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponseDto {
    private String msg;
    private Integer statusCode;

    public ApiResponseDto(String msg,Integer statusCode){
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
