package com.blogapi.blogplatform.repository;

import com.blogapi.blogplatform.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthorId(Long userId);

}