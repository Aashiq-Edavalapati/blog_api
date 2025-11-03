package com.blogapi.blogplatform.service;

import com.blogapi.blogplatform.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> getAllPosts();

    Optional<Post> getPostById(Long postId);


    Post createPost(Post post, Long authorId);

    void deletePost(Long postId);

}
