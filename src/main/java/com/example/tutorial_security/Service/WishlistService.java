package com.example.tutorial_security.Service;


import com.example.tutorial_security.Model.Cart;
import com.example.tutorial_security.Model.Product;
import com.example.tutorial_security.Model.Wishlist;
import com.example.tutorial_security.Repository.CartRepository;
import com.example.tutorial_security.Repository.ProductRepository;
import com.example.tutorial_security.Repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;



    public String deleteProductFromWishlist(int cartId, Long productId) {
        Wishlist existingWishlist = new Wishlist();
        List<Product> newList = new ArrayList<>();
        Product product = new Product();
        existingWishlist = wishlistRepository.findById(cartId).orElse(null);
        if(existingWishlist == null){
            return null;
        }
        newList = existingWishlist.getProductList();
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
        existingWishlist.setProductList(newList);
        wishlistRepository.save(existingWishlist);
        return "Successfully deleted";
    }

}
