package com.example.tutorial_security.Service;

import com.example.tutorial_security.Model.Product;
import com.example.tutorial_security.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProduct(String name, Integer id) {
        Product product = new Product();
        product.setName(name);
        product.setId(id);

        productRepository.save(product);
    }

    public void updateProduct(int productId, Product product) {
    }
}
