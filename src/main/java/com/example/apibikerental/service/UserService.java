package com.example.apibikerental.service;

import com.example.apibikerental.model.Bike;
import com.example.apibikerental.model.User;
import com.example.apibikerental.repository.UserRepository;
import com.example.apibikerental.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void registerUser(User user) {
        // Check if user with the same username or email already exists
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already exists.");
        }
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Generate and return JWT token
            return generateJwtToken(user);
        }
        return null;
    }

    private String generateJwtToken(User user) {
        // Implement JWT token generation
        return jwtUtil.generateToken(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

}
