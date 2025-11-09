package com.blogapi.blogplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
        @NotBlank(message = "Title cannot be blank")
        @Size(max = 255, message = "Title cannot have more than 255 characters")
        String title,

        @NotBlank(message = "Content cannot be blank")
        String content
) {
}
