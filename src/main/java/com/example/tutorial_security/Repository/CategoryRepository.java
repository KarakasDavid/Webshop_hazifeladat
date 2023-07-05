package com.example.tutorial_security.Repository;

import com.example.tutorial_security.Model.Category;
import com.example.tutorial_security.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByName(String categoryName);
}
