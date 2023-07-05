package com.example.tutorial_security.Repository;

import com.example.tutorial_security.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
