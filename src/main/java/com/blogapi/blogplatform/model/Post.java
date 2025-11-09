package com.blogapi.blogplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT") // Use TEXT for longer content
    private String content;

    @CreationTimestamp // Automatically set this field on creation
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Automatically set this field on update
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // --- RELATIONSHIP with USER ---
    @ManyToOne(fetch = FetchType.LAZY) // Many posts can belong to one use. FetchType.LAZY => Don't load the `User` data from the DB until we explicitly call post.getAuthor().
    @JoinColumn(name = "author_id", nullable = false) // Foreign key
    private User author;

    // --- RELATIONSHIP with COMMENTS ---
    @JsonIgnore
    @OneToMany(
            mappedBy = "post", // post is the field name in Comment class
            cascade = CascadeType.ALL, // If a Post is deleted, delete all its comments
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();
}
