package com.blogapi.blogplatform.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank(message = "Comment content cannot be blank")
        String content
) {}
