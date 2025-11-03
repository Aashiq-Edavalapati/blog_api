package com.blogapi.blogplatform.repository;

import com.blogapi.blogplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // "SELECT * FROM blog_users WHERE username = ?"
    Optional<User> findByUsername(String username);

    // "SELECT * FROM blog_users WHERE email = ?"
    Optional<User> findByEmail(String email);

}
