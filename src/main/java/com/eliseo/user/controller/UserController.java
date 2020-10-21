package com.eliseo.user.controller;

import com.eliseo.user.dto.MessageDTO;
import com.eliseo.user.dto.UserRequestDTO;
import com.eliseo.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity save(@RequestBody UserRequestDTO userRequestDTO){
        return this.userService.saveUser(userRequestDTO);
    }

    @GetMapping("/")
    public ResponseEntity hello(){
        return ResponseEntity.ok(new MessageDTO("HOLA"));
    }


}
