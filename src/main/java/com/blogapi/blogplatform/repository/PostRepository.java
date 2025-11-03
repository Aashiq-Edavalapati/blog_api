package com.blogapi.blogplatform.repository;

import com.blogapi.blogplatform.model.Post;

import java.util.List;

public interface PostRepository {

    List<Post> findByAuthorId(Long userId);

}
