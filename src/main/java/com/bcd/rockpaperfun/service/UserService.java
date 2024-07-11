package com.bcd.rockpaperfun.service;

import com.bcd.rockpaperfun.entity.User;

public interface UserService {

    public User signUp(String username, String password);

    public User findByUsername(String username);

    public User findById(Long id);
}
