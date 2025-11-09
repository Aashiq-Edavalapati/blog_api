package com.blogapi.blogplatform.dto;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String authorUsername,
        Long postId
) {}