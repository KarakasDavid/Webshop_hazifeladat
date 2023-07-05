package com.example.tutorial_security.Security;


import com.example.tutorial_security.Model.User;
import com.example.tutorial_security.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests((requests) -> requests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/home", "/hello", "/add", "/h2-console/**", "/register", "/register_page").permitAll()
                        .requestMatchers("/add", "/adding_page").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }



    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetailsList = new ArrayList<>();


        // Retrieve users from the UserRepository
        List<User> userList = userRepository.findAll();


        // Iterate over the users and create UserDetails objects
        for (User user : userList) {
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withDefaultPasswordEncoder() // Use withDefaultPasswordEncoder
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles("USER") // Set the user role (you can modify as needed)
                    .build();
            userDetailsList.add(userDetails);
        }
        System.out.println(userDetailsList.get(0).getUsername());

        UserDetails user1 = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
        userDetailsList.add(user1);

        return new InMemoryUserDetailsManager(userDetailsList);
    }




}