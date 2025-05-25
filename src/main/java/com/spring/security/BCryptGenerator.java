package com.spring.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        // The raw password
        String rawPassword = "4321";
        
        // Generate the hash using BCrypt
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        // Print the encoded password
        System.out.println("Encoded password: " + encodedPassword);
    }
}
