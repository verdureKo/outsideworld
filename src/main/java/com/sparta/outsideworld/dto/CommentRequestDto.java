package com.sparta.outsideworld.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "?? ?? ????.")
    private String comment;
}
