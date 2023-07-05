package com.example.tutorial_security.Controller;

import com.example.tutorial_security.Model.Cart;
import com.example.tutorial_security.Model.User;
import com.example.tutorial_security.Model.UserDTO;
import com.example.tutorial_security.Model.Wishlist;
import com.example.tutorial_security.Repository.CartRepository;
import com.example.tutorial_security.Repository.UserRepository;
import com.example.tutorial_security.Repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class UserController {

    //A szükséges repository-k
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private WishlistRepository whislistRepository;


    /*
    Egy felhasználó regisztálásáért felelős függvény. Paraméterül egy UserDTO-t kap,
     ami a felhasználó alap tulajdonságait tartalmazza, mint felhasználónév és jelszó.
     Amennyiben a felhasználó már létezik hibaüzenettel térünk vissza.
     */
    @PostMapping("/register")
    public RedirectView register(@ModelAttribute UserDTO userDTO) {
        Logger.getAnonymousLogger().log(Level.INFO,"Regisztráció");

        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            // Egy hibaüzenettel rendelkező oldalra küld
            Logger.getAnonymousLogger().log(Level.INFO,"A felhasználó név foglalt volt.");
            return new RedirectView("/error_page?message=Username already exists");
        }
        //A UserDTO adatait felhasználó rész, ahol a felhasználók listájába kerül
        Cart cart = new Cart();
        Wishlist whislist = new Wishlist();
        cartRepository.save(cart);
        whislistRepository.save(whislist);
        User newUser = new User(userDTO.getUsername(), userDTO.getPassword(), cart, cart.getId(),whislist,whislist.getId());
        userRepository.save(newUser);
        Logger.getAnonymousLogger().log(Level.INFO,"Regisztráció sikeres");
        return new RedirectView("/register_page");
    }

}
