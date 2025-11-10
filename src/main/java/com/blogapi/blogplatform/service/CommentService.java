package com.blogapi.blogplatform.service;

import com.blogapi.blogplatform.dto.CommentRequest;
import com.blogapi.blogplatform.dto.CommentResponse;
import com.blogapi.blogplatform.dto.CommentUpdateRequest;
import com.blogapi.blogplatform.model.User;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(Long postId, CommentRequest commentRequest, User currUser);

    List<CommentResponse> getCommentsByPostId(Long postId);

    void deleteComment(Long commentId, User currUser);

    CommentResponse updateComment(Long commentId, CommentUpdateRequest commentRequest, User currUser);

}
