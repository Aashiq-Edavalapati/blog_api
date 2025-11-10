package com.blogapi.blogplatform.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest(
        @NotBlank(message = "Comment cannot be blank")
        String content
) {}