package com.example.AuthController.repositary;

import com.example.AuthController.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUserName(String username);
}
