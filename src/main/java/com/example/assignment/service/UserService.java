package com.example.assignment.service;

import com.example.assignment.entity.User;

import java.util.List;

public interface UserService {
    void loadUsers();
    List<User> searchUsers(String query);
    User getUserById(Long id);
    User getUserByEmail(String email);
}
