package com.blogapi.blogplatform.controller;

import com.blogapi.blogplatform.model.Post;
import com.blogapi.blogplatform.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    // Constructor injection
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // GET /api/posts
    @GetMapping("/")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // GET /api/posts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(post -> ResponseEntity.ok(post)) // 200 OK
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    // POST /api/posts
    @PostMapping("/")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        // TODO: Get authorId from the authenticated user (Spring Security)
        // For now, hardcoded authorId
        Long tempAuthorId = 1L;

        Post createdPost = postService.createPost(post, tempAuthorId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED); // 201 Created
    }

    // DELETE /api/posts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        // TODO: Add security to check if the user is the author

        postService.deletePost(id);
        return ResponseEntity.noContent().build(); // 204 Successful deletion
    }

}
