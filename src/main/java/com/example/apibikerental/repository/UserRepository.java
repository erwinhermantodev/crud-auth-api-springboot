package com.example.apibikerental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.apibikerental.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    // No need to define 'save' method here, it's inherited from JpaRepository

}
