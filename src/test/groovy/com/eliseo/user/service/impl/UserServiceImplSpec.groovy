package com.eliseo.user.service.impl

import com.eliseo.user.domain.UserRegister
import com.eliseo.user.dto.PhoneDTO
import com.eliseo.user.dto.UserRequestDTO
import com.eliseo.user.repository.UserRepository
import com.eliseo.user.service.UserService
import spock.lang.Specification

class UserServiceImplSpec extends Specification {


    public void "save user"() {
        given: "una solicitud de usuario"
        PhoneDTO phoneDTOSample = PhoneDTO.builder()
        .cityCode("1")
        .countryCode("1")
        .number("123")

        List<PhoneDTO> phoneDTOList = new LinkedList<>()
        phoneDTOList.add(phoneDTOSample)

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .email("eliseohh@gmail.com")
                .name("Eliseo Henriquez")
                .password("somepassword")
                .phones(phoneDTOList)
                .build()

        and: "entregará el siguiente objeto"
        UserRegister sampleUserRegister = new UserRegister()
        //TODO: setear campos de datos para user registry

        and: "se forma la entidad"

        and: "el repositorio del servicio retornará la entidad ya formada"
        UserRepository userRepository = Stub(UserRepository)
        UserService sampleUserService = new UserServiceImpl(userRepository)
        userRepository.save(_ as UserRegister) >> sampleUserRegister

        when: "se consulta respecto a la info del usuario"

        then: "se espera la siguiente información"

    }
}
