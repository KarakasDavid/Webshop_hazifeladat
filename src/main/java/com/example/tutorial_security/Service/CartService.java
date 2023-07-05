package com.example.tutorial_security.Service;

import com.example.tutorial_security.Model.Cart;
import com.example.tutorial_security.Model.Product;
import com.example.tutorial_security.Repository.CartRepository;
import com.example.tutorial_security.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {


    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;



    public String deleteProductFromCart(int cartId, Long productId) {
        Cart existingCart = new Cart();
        List<Product> newList = new ArrayList<>();
        Product product = new Product();
        existingCart = cartRepository.findById(cartId).orElse(null);
        if(existingCart == null){
            return null;
        }
        newList = existingCart.getProductList();
        product = productRepository.findById(productId).orElse(null);
        if(product == null){
            return null;
        }
        for(int i = 0; i < newList.size(); i++){
            if(newList.get(i) == product){
                newList.remove(i);
            }
        }
        productRepository.save(product);
        existingCart.setProductList(newList);
        cartRepository.save(existingCart);
        return "Successfully deleted";
    }

}
