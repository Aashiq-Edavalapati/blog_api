package com.blogapi.blogplatform.service.impl;

import com.blogapi.blogplatform.dto.CommentRequest;
import com.blogapi.blogplatform.dto.CommentResponse;
import com.blogapi.blogplatform.dto.CommentUpdateRequest;
import com.blogapi.blogplatform.exception.ResourceNotFoundException;
import com.blogapi.blogplatform.exception.UnauthorizedException;
import com.blogapi.blogplatform.model.Comment;
import com.blogapi.blogplatform.model.Post;
import com.blogapi.blogplatform.model.User;
import com.blogapi.blogplatform.repository.CommentRepository;
import com.blogapi.blogplatform.repository.PostRepository;
import com.blogapi.blogplatform.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentResponse createComment(Long postId, CommentRequest commentRequest, User currUser) {
        // Check if the post exists
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Create new comment entity
        Comment newComment = new Comment();
        newComment.setAuthor(currUser);
        newComment.setPost(post);
        newComment.setContent(commentRequest.content());

        // Save to DB
        Comment savedComment = commentRepository.save(newComment);

        // Map the response to DTO and return
        return mapToCommentResponse(savedComment);
    }

    private CommentResponse mapToCommentResponse(Comment savedComment) {
        return new CommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getAuthor().getUsername(),
                savedComment.getPost().getId()
        );
    }

    @Override
    public List<CommentResponse> getCommentsByPostId(Long postId) {
        // Check if the post exists
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post", "id", postId);
        }

        return commentRepository.findByPostId(postId).stream()
                .map(this::mapToCommentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId, User currUser) {
        // Check if the comment exists
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // Check if curr user is the author of the comment
        if (!comment.getAuthor().getId().equals(currUser.getId())) {
            throw new UnauthorizedException("You are not authorized to delete this comment!");
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentResponse updateComment(Long commentId, CommentUpdateRequest commentRequest, User currUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getAuthor().getId().equals(currUser.getId())) {
            throw new UnauthorizedException("You are not authorized to update this comment!");
        }

        comment.setContent(commentRequest.content());
        Comment updatedComment = commentRepository.save(comment);
        return mapToCommentResponse(comment);
    }
}
