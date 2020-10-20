package com.eliseo.user.service.impl;

import com.eliseo.user.dto.UserRequestDTO;
import com.eliseo.user.repository.UserRepository;
import com.eliseo.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<String> saveUser(UserRequestDTO userRequestDTO) {
        return null;
    }
}
