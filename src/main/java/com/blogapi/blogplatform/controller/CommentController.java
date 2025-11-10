package com.blogapi.blogplatform.controller;

import com.blogapi.blogplatform.dto.CommentRequest;
import com.blogapi.blogplatform.dto.CommentResponse;
import com.blogapi.blogplatform.dto.CommentUpdateRequest;
import com.blogapi.blogplatform.model.User;
import com.blogapi.blogplatform.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // POST /api/posts/{postId}/comments
    @PostMapping("/")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest commentRequest,
            Authentication authentication) {
        User currUser = (User) authentication.getPrincipal();

        CommentResponse createdComment = commentService.createComment(postId, commentRequest, currUser);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // GET /api/posts/{postId}/comments
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // PUT /api/posts/{postId}/comments/{commentId}
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequest commentRequest,
            Authentication authentication
    ) {
        User currUser = (User) authentication.getPrincipal();
        CommentResponse updatedComment = commentService.updateComment(commentId, commentRequest, currUser);

        return ResponseEntity.ok(updatedComment);
    }

    // DELETE /api/posts/{postId}/comments/{commentId}
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            Authentication authentication
    ) {
        User currUser = (User) authentication.getPrincipal();
        commentService.deleteComment(commentId, currUser);

        return ResponseEntity.noContent().build();
    }

}
