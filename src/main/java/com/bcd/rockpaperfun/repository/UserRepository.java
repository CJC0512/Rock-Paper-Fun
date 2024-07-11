package com.bcd.rockpaperfun.repository;

import com.bcd.rockpaperfun.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}