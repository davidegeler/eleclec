package com.lb3.webshop.services;

import com.lb3.webshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lb3.webshop.models.User;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> saveUser(User user){
        return Optional.ofNullable(this.userRepository.save(user));
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password){
        return this.userRepository.findByEmailAndPassword(email, password);
    }

}
