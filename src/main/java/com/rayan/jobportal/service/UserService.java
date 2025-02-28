package com.rayan.jobportal.service;

import com.rayan.jobportal.entity.User;
import com.rayan.jobportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addNewUser(User user) {
        user.setActive(true);
        user.setRegistrationDate(new Date(System.currentTimeMillis()));
        return userRepository.save(user);
    }


    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
