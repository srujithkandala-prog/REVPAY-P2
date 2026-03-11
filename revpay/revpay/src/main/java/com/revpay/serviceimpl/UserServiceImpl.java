package com.revpay.serviceimpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.revpay.entity.User;
import com.revpay.repository.UserRepository;
import com.revpay.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public User registerUser(User user) {
        logger.info("Registering new user with email: {}", user.getEmail());
        user.setPassword(encoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        logger.debug("User registered successfully with ID: {}", saved.getId());
        return saved;
    }
}