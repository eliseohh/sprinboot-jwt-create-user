package com.eliseo.user.controller;

import com.eliseo.user.dto.UserRequestDTO;
import com.eliseo.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<String> save(@RequestBody UserRequestDTO userRequestDTO){
        return this.userService.saveUser(userRequestDTO);
    }


}
