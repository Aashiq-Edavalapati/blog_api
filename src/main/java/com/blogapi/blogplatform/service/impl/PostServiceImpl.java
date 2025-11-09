package com.blogapi.blogplatform.service.impl;

import com.blogapi.blogplatform.dto.PostUpdateRequest;
import com.blogapi.blogplatform.exception.ResourceNotFoundException;
import com.blogapi.blogplatform.exception.UnauthorizedException;
import com.blogapi.blogplatform.model.Post;
import com.blogapi.blogplatform.model.User;
import com.blogapi.blogplatform.repository.PostRepository;
import com.blogapi.blogplatform.repository.UserRepository;
import com.blogapi.blogplatform.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // Constructor injection (Dependency injection)
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    @Override
    public Post createPost(Post post, Long authorId) {
        // 1. Find the author User by their ID
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", authorId));

        // 2. Set the found author on the post object
        post.setAuthor(author);

        // 3. Save the post to the database
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId, User currUser) {
        // 1. Find the post by ID
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // 2. Get the author of the post
        User author = post.getAuthor();

        // 3. Compare the author's ID with current user's ID
        if (!author.getId().equals(currUser.getId())) {
            throw new UnauthorizedException("You are not allowed to delete this post.");
        }

        // 4. If they match, delete the post
        postRepository.deleteById(postId);
    }

    @Override
    public Post updatePost(Long postId, PostUpdateRequest postUpdateRequest, User currUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // Check if the current user is the author
        if (!post.getAuthor().getId().equals(currUser.getId())) {
            throw new UnauthorizedException("You are not authorized to update this post!");
        }

        // Update the fields
        post.setTitle(postUpdateRequest.title());
        post.setContent(postUpdateRequest.content());

        // Save the post to DB
        return postRepository.save(post);
    }

}
