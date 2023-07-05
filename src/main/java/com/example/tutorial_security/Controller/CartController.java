package com.example.tutorial_security.Controller;

import com.example.tutorial_security.Model.Cart;
import com.example.tutorial_security.Model.Product;
import com.example.tutorial_security.Repository.CartRepository;
import com.example.tutorial_security.Repository.ProductRepository;
import com.example.tutorial_security.Repository.UserRepository;
import com.example.tutorial_security.Service.CartService;
import com.example.tutorial_security.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class CartController {

    private final CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    public CartController(CartService cartService) {
        this.cartService = cartService;
    }



    /*
    Ez a metódus felel a a product-ok felvéteréért a cart--ba
    Paraméterként megkapja a productot azonosító ID-t,
    amely alapján megkeresi azt és hozzáadja az éppen aktív felhasználó cart product listájához
     */
    @PostMapping("/cart/addProduct")
    public String addProductToCart(@RequestParam Long cartId, @RequestParam Long productId) {
        Logger.getAnonymousLogger().log(Level.INFO,"Termék kocsihoz adása");
        //megkeressük az aktuális felhasználó cart-ját
        String currentUsername = UserUtils.getCurrentUsername();
        Cart cart = cartRepository.findById(userRepository.findByUsername(currentUsername).getCartId()).orElseThrow(() -> new IllegalArgumentException("Invalid cart ID"));

        // megkeressük a terméket
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        // hozzáadjuk a listához a terméket
        cart.getProductList().add(product);
        cart.addTotal(product.getPrice());


        // Elmentjük a cartot
        cartRepository.save(cart);

        return "Added product to cart!";
    }


    /*
    Ez a metódus felel a productok cartból való kivételéért.
    Paraméterben megkapja a Productnak az azonosítóját, amit kivesz
    az éppen aktív felhasználó kosarából
     */
    @PostMapping("/cart/removeProduct")
    public String addRemoveProductFromCart(@RequestParam Long cartId, @RequestParam Long productId) {

        Logger.getAnonymousLogger().log(Level.INFO,"Termék kocsibol törlése");
        String currentUsername = UserUtils.getCurrentUsername();
        cartService.deleteProductFromCart(userRepository.findByUsername(currentUsername).getCartId(),productId);


    return "Removed product from cart";
    }

    /*
    Ez a függénvy fele a cart product listájának kilistázásáért.
    Az éppen aktuális felhasználó kosarának tartalmát listázza ki.
     */
    @GetMapping("/cart/list")
    public ModelAndView listCartProducts(@RequestParam Long cartId) {
        Logger.getAnonymousLogger().log(Level.INFO,"Kocsi kilistázása");
        // Megkeresi az éppen aktuális felhasználó cartját
        String currentUsername = UserUtils.getCurrentUsername();
        Cart cart = cartRepository.findById(userRepository.findByUsername(currentUsername).getCartId()).orElseThrow(() -> new IllegalArgumentException("Invalid cart ID"));

        // Megszerzi a cart product listáját
        List<Product> productList = cart.getProductList();

        // ModelAndView object elkészítése és a megjelenítendő nézet megadása
        ModelAndView modelAndView = new ModelAndView("cart-list");

        // A lista modelhez adása
        modelAndView.addObject("cart", cart);
        modelAndView.addObject("productList", productList);


        return modelAndView;
    }


}
