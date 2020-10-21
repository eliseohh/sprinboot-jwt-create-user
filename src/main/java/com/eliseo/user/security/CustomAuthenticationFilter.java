package com.eliseo.user.security;

import com.eliseo.user.domain.User;
import com.eliseo.user.utils.CustomUserConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.eliseo.user.utils.TokenManager.TOKEN_PREFIX;
import static com.eliseo.user.utils.TokenManager.generateToken;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String AUTH_HEADER_KEY = "Authorization";

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(CustomUserConverter.toAuthenticationToken(user));
        } catch (IOException e) {
            throw new AuthenticationCredentialsNotFoundException("Failed to resolve authentication credentials", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        response.addHeader(AUTH_HEADER_KEY,
                TOKEN_PREFIX + generateToken(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername()));
    }
}
