package com.aryan.neosurge.service;

import com.aryan.neosurge.model.User;

public interface UserService {

    void saveUser(User user);

    boolean isUserExists(String username);
}
