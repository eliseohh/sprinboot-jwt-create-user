package com.eliseo.user.service.impl

import com.eliseo.user.repository.UserRepository
import com.eliseo.user.utils.CustomUserConverter
import spock.lang.Shared
import spock.lang.Specification

class CustomUserDetailsServiceTest extends Specification {

    private UserRepository userRepository;
    private CustomUserDetailsService customUserDetailsService;
    @Shared
    private CustomUserConverter customUserConverter;

    def setup() {
        userRepository = Stub(UserRepository)
        customUserDetailsService = new CustomUserDetailsService(userRepository)
        customUserConverter = new CustomUserConverter()
    }

    void testLoadUserByUsername() {
        given: "dado un username de tipo email"
        String username = "eliseohh@gmail.com"

        and: "esperara un objeto UserDetails"

        and: "y lo hará a través de customUserConverter, retornando un user"


        when: "cuando se cargue un usuario por username"

        then: "retorna un usuario por email"

    }

    void testLoadUserByUsername_when_throw_UsernameNotFoundException() {
        given: "dado un username de tipo email"
        String username = "eliseohh@gmail.com"

        and: "esperara un objeto UserDetails"

        and: "y lo hará a través de customUserConverter, retornando un user"


        when: "cuando se cargue un usuario por username"

        then: "lanza una excepción de UsernameNotFoundException"

    }
}
