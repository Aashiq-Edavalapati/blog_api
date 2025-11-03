package com.blogapi.blogplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    // --- RELATIONSHIP ---
    @ManyToOne(fetch = FetchType.LAZY) // Many posts can belong to one use. FetchType.LAZY => Don't load the `User` data from the DB until we explicitly call post.getAuthor().
    @JoinColumn(name = "author_id", nullable = false) // Foreign key
    private User author;

}
