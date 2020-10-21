package com.eliseo.user.utils

import com.eliseo.user.domain.User
import org.mockito.Mockito
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Shared
import spock.lang.Specification

class CustomUserConverterTest extends Specification {

    @Shared
    CustomUserConverter customUserConverter

    User user

    def setup() {
        customUserConverter = new CustomUserConverter()
        user = User.builder()
                .name(UUID.randomUUID().toString())
                .email(UUID.randomUUID().toString())
                .password(UUID.randomUUID().toString())
                .phones(Mockito.anyList())
                .build()
    }

    void testToUser() {
        given: "dado un usuario de tipo User interno"

        when: "retornará una instancia User de spring security"
        org.springframework.security.core.userdetails.User userConverted = customUserConverter.toUser(user)

        then: "el objeto no debe ser nulo"
        assert userConverted != null

    }

    void testToAuthenticationToken() {
        given: "dado un usuario de tipo User interno"

        when: "retornará una instancia de UsernamePasswordAuthenticationToken"
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = customUserConverter.toAuthenticationToken(user)

        then: "el objeto no debe ser nulo"
        assert usernamePasswordAuthenticationToken != null
    }
}
