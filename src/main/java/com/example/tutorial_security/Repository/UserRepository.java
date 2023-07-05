package com.example.tutorial_security.Repository;

import com.example.tutorial_security.Model.Product;
import com.example.tutorial_security.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String str);
}
