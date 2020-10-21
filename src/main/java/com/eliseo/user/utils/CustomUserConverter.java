package com.eliseo.user.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

public class CustomUserConverter {
    public static User toUser(com.eliseo.user.domain.User user) {
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

    public static UsernamePasswordAuthenticationToken toAuthenticationToken(com.eliseo.user.domain.User user) {
        return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
