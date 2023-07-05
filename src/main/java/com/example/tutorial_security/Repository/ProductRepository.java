package com.example.tutorial_security.Repository;



import com.example.tutorial_security.Model.Product;
import com.example.tutorial_security.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long id);
    Product findByName(String str);


}