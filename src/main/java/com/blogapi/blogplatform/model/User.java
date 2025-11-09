package com.blogapi.blogplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data // Lombok: Generates getters, setters, toString(), equals(), and hashCode()
@NoArgsConstructor // Lombok: Generates a no-argument constructor
@AllArgsConstructor // Lombok: Generates an all-argument constructor
@Entity // JPA: Marks this class as a database entity
@Table(name = "blog_users") // JPA: Specifies the table name as "blog_users" (to avoid conflict with "user" keyword)
public class User implements UserDetails {

    @Id // JPA: Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB: Auto-increments the ID
    private Long id;

    @Column(nullable = false, unique = true) // DB: Cannot be null, must be unique
    private String username;

    @Column(nullable = false, unique = true) // DB: Cannot be null, must be unique
    private String email;

    @Column(nullable = false) // DB: Cannot be null
    private String password;

    // --- RELATIONSHIP ---
    @JsonIgnore
    @OneToMany(
            mappedBy = "author", // "author" is the field name in the Post class
            cascade = CascadeType.ALL, // If a user is deleted, delete all their posts
            orphanRemoval = true, // If a post is removed from this list, delete it
            fetch = FetchType.LAZY
    )
    private List<Post> posts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}