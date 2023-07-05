package com.example.tutorial_security.Model;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Wishlist {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "whislist_id")
    private List<Product> wishlist;

    @Column(name = "total")
    private Integer total = 0;

    //getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setProductList(List<Product> productList) {
        this.wishlist = productList;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getTotal() {
        return total;
    }

    public List<Product> getProductList() {
        return wishlist;
    }

    public User getUser() {
        return user;
    }

    public void addTotal(Integer price) {
        this.total = price;
    }

}
