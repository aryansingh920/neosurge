package com.aryan.neosurge.service.impl;

import com.aryan.neosurge.model.User;
import com.aryan.neosurge.repository.UserRepository;
import com.aryan.neosurge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean isUserExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
