package com.example.tutorial_security.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    @Size(min=1,max=10,message="Product name should be at least 1 characters max 30 characters")
    private String username;

    @Column(nullable = false)
    @Size(min=1,max=10,message="Product name should be at least 1 characters max 30 characters")
    private String password;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Cart cart;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Wishlist whislist;

    @Column(name = "cart_id") // Map to the cart_id column in the users table
    private Integer cartId;

    @Column(name = "whislist_id") // Map to the cart_id column in the users table
    private Integer whislistId;

    // getters, and setters

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getWhislistId() {
        return whislistId;
    }

    public void setWhislistId(Integer whislistId) {
        this.whislistId = whislistId;
    }
    // Constructors, getters, and setters

    public User() {
    }
    public User(String username,String password, Integer id){
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password,Cart cart,Integer cart_id) {
        this.username = username;
        this.password = password;
        this.cart = cart;
        this.cartId = cart_id;
    }

    public User(String username, String password, Cart cart, Integer cart_id, Wishlist whislist, Integer whislist_id) {
        this.username = username;
        this.password = password;
        this.cart = cart;
        this.cartId = cart_id;
        this.whislist = whislist;
        this.whislistId = whislist_id;

    }

    //getters and setters

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }

    public Wishlist getWhislist() {
        return whislist;
    }

    public void setWhislist(Wishlist whislist) {
        this.whislist = whislist;
    }
}
