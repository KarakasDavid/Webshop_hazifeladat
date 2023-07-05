package com.example.tutorial_security.Controller;

import com.example.tutorial_security.Model.Product;
import com.example.tutorial_security.Model.Wishlist;
import com.example.tutorial_security.Repository.ProductRepository;
import com.example.tutorial_security.Repository.UserRepository;
import com.example.tutorial_security.Repository.WishlistRepository;
import com.example.tutorial_security.Service.WishlistService;
import com.example.tutorial_security.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class WishlistController {

    private final WishlistService wishlistService;

    @Autowired
    WishlistRepository wishlistrepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }


    /*
    Ez a metódus felel a a product-ok felvéteréért a wishlistbe
    Paraméterként megkapja a productot azonosító ID-t,
    amely alapján megkeresi azt és hozzáadja az éppen aktív felhasználó wishlist product listájához
     */

    @PostMapping("/wishlist/addProduct")
    public String addProductToWishlist(@RequestParam Integer wishlistId, @RequestParam Long productId) {
        Logger.getAnonymousLogger().log(Level.INFO,"Termék kívánságlistához adása");
        //megkeressük az aktuális felhasználó wishlistjét
        String currentUsername = UserUtils.getCurrentUsername();
        Wishlist wishlist= wishlistrepository.findById(userRepository.findByUsername(currentUsername).getWhislistId()).orElseThrow(() -> new IllegalArgumentException("Invalid wishlist ID"));

        // megkeressük a terméket
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));

        // hozzáadjuk a listához a terméket
        wishlist.getProductList().add(product);
        wishlist.addTotal(product.getPrice());


        // Elmentjük a wishlistet
        wishlistrepository.save(wishlist);

        return "Added product to wishlist";
    }

    /*
     Ez a függénvy fele a wishlist product listájának kilistázásáért.
     Az éppen aktuális felhasználó kosarának tartalmát listázza ki.
      */
    @GetMapping("/wishlist/list")
    public ModelAndView listWishlistProducts(@RequestParam Integer whislist_id) {

        Logger.getAnonymousLogger().log(Level.INFO,"Kívánságlista kilistázása");
        // Megkeresi az éppen aktuális felhasználó wishlistjét
        String currentUsername = UserUtils.getCurrentUsername();
        System.out.println(userRepository.findByUsername(currentUsername).getWhislistId());
        Wishlist wishlist= wishlistrepository.findById(userRepository.findByUsername(currentUsername).getWhislistId()).orElseThrow(() -> new IllegalArgumentException("Invalid wishlist ID"));

        // Megszerzi a wishliszt product listáját
        List<Product> productList = wishlist.getProductList();

        // ModelAndView object elkészítése és a megjelenítendő nézet megadása
        ModelAndView modelAndView = new ModelAndView("wishlist-list");

        // A lista modelhez adása
        modelAndView.addObject("whislist", wishlist);
        modelAndView.addObject("wishlist", productList);


        return modelAndView;
    }

    /*
    Ez a metódus felel a productok wishlistből való kivételéért.
    Paraméterben megkapja a Productnak az azonosítóját, amit kivesz
    az éppen aktív felhasználó wishlistjéből
     */

    @PostMapping("/wishlist/removeProduct")
    public String addRemoveProductFromWishlist(@RequestParam Long cartId, @RequestParam Long productId) {
        Logger.getAnonymousLogger().log(Level.INFO,"Termék törlése a kívásnáglistából");
        String currentUsername = UserUtils.getCurrentUsername();
        wishlistService.deleteProductFromWishlist(userRepository.findByUsername(currentUsername).getWhislistId(),productId);


        return "Removed product from wishlist";
    }


}
