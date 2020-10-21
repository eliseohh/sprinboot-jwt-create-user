package com.eliseo.user.controller;

import com.eliseo.user.dto.MessageDTO;
import com.eliseo.user.dto.UserRequestDTO;
import com.eliseo.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity save(@RequestBody UserRequestDTO userRequestDTO){
        log.info("/user/ [INIT transaction]");
        ResponseEntity responseEntity = this.userService.saveUser(userRequestDTO);
        log.info("/user/ [FIN_OK transaction]");
        return responseEntity;
    }

    @GetMapping("/")
    public ResponseEntity hello(){
        return ResponseEntity.ok(new MessageDTO("HOLA"));
    }


}
