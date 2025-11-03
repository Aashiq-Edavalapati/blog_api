package com.blogapi.blogplatform.service.impl;

import com.blogapi.blogplatform.model.Post;
import com.blogapi.blogplatform.model.User;
import com.blogapi.blogplatform.repository.PostRepository;
import com.blogapi.blogplatform.repository.UserRepository;
import com.blogapi.blogplatform.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

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
                .orElseThrow(() -> new RuntimeException("Error: Author not found with id: " + authorId));

        // 2. Set the found author on the post object
        post.setAuthor(author);

        // 3. Save the post to the database
        return postRepository.save(post);
    }

    @Override
    public void deletePost(Long postId) {
        // TODO: Add logic to check if the current user is the author(Auth)

        // For now, just delete the post
        postRepository.deleteById(postId);
    }

}
