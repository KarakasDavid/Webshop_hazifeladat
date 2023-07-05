package com.example.tutorial_security.Controller;



import com.example.tutorial_security.Model.Category;
import com.example.tutorial_security.Model.Product;
import com.example.tutorial_security.Repository.CategoryRepository;
import com.example.tutorial_security.Repository.ProductRepository;
import com.example.tutorial_security.Repository.UserRepository;
import com.example.tutorial_security.Service.ProductService;
import com.example.tutorial_security.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class ProductController {


    private final ProductService productService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /*
    Ez a függvény felel az új productok felvételéért. Paraméterül kapja a tárgy nevét, árát, és kategóriáját.
    Ezek alapján létrehoz egy új productot és felvveszi a productok listájához.
     */
    @PostMapping("/add")
    public String addProductToCart(@RequestParam String name, @RequestParam Integer price, @RequestParam String categoryName) {
        Logger.getAnonymousLogger().log(Level.INFO,"Termék hozzáadva a termékek listájához");

        // Create a new Product instance
        if(productRepository.findByName(name)==null)
        {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            System.out.println(categoryName);

            Category category = categoryRepository.findByName(categoryName);
            if (category == null) {
                // Handle the case when the category does not exist
                return "category_not_found";
            }
            product.setCategory(category);

            // Save the product to the repository
            productRepository.save(product);

            return "successfull_addition";
        }
        else
            return"Already exists";

    }


    /*
    Ez a függvény felel a prudoctok kilistázásáért.
     */
    @GetMapping("/list")
    public ModelAndView getProductList() {
        Logger.getAnonymousLogger().log(Level.INFO,"Termékek kilistázása");

        // Retrieve the product list from the repository
        Iterable<Product> products = productRepository.findAll();

        // Create a ModelAndView object and set the view name
        ModelAndView modelAndView = new ModelAndView("product-list");

        // Add the product list to the model
        modelAndView.addObject("products", products);

        return modelAndView;
    }


    /*
    Ez a függvény megkeres egy terméket id alapján a listából.
    Az id-t paraméterül kapja.
     */
    @GetMapping("/find/id/{id}")
    public String findProductById(@PathVariable Long id, Model model) {
        Logger.getAnonymousLogger().log(Level.INFO,"Termék megkeresése ID alapján");
        Product product = productRepository.findProductById(id);
        model.addAttribute("product", product);
        return "product-details";
    }


    /*
    Ez a függvény megkeres egy terméket név alapján.
    A nevet paraméterként kapja.
     */
    @GetMapping("/find/name/{name}")
    public ModelAndView findProductByName(@PathVariable String name) {
        Logger.getAnonymousLogger().log(Level.INFO, "Termék megkeresése név alapján");
        Product product = productRepository.findByName(name);
        System.out.println(name);

        ModelAndView modelAndView = new ModelAndView("product-details");
        modelAndView.addObject("product", product);

        return modelAndView;
    }
}