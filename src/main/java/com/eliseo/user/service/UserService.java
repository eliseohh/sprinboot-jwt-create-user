package com.eliseo.user.service;

import com.eliseo.user.dto.UserRequestDTO;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity saveUser(UserRequestDTO userRequestDTO);
}
