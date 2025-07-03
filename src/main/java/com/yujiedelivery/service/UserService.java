package com.yujiedelivery.service;

import com.yujiedelivery.model.User;
import java.util.List;

public interface UserService {
    User getCurrentUser();
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
    User updateUserRole(Long id, User.UserRole role);
} 