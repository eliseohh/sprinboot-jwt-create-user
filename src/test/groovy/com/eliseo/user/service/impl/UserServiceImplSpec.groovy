package com.eliseo.user.service.impl

import com.eliseo.user.domain.User
import com.eliseo.user.dto.MessageDTO
import com.eliseo.user.dto.PhoneDTO
import com.eliseo.user.dto.UserRequestDTO
import com.eliseo.user.dto.UserResponseDTO
import com.eliseo.user.repository.UserRepository
import com.eliseo.user.service.UserService
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

import java.sql.Date

class UserServiceImplSpec extends Specification {

    private UserRepository userRepository
    private PasswordEncoder passwordEncoder
    private PhoneDTO phoneDTOSample

    def setup() {
        userRepository = Stub(UserRepository)
        passwordEncoder = Stub(PasswordEncoder)
        phoneDTOSample = PhoneDTO.builder()
                .cityCode("1")
                .countryCode("1")
                .number("123")
                .build()
    }

    public void "guardar usuario"() {
        given: "una solicitud de usuario"

        List<PhoneDTO> phoneDTOList = new LinkedList<>()
        phoneDTOList.add(phoneDTOSample)

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .email("eliseohh@gmail.com")
                .name("Eliseo Henriquez")
                .password("Anj1G@de12")
                .phones(phoneDTOList)
                .build()

        and: "se forma la entidad y entregará el siguiente objeto"
        User sampleUserRegister = User.builder()
                .id(UUID.randomUUID().toString())
                .lastLogin(Mockito.any(Date.class))
                .token(Mockito.anyString())
                .isActive(Mockito.anyBoolean())
                .name(Mockito.anyString())
                .email(Mockito.anyString())
                .password(Mockito.anyString())
                .phones(Mockito.anyList())
                .build()

        and: "el repositorio del servicio retornará la entidad ya formada"
        UserService sampleUserService = new UserServiceImpl(userRepository, passwordEncoder)
        passwordEncoder.encode(_ as String) >> Mockito.anyString()
        userRepository.save(_ as User) >> sampleUserRegister

        when: "se guarda info del usuario"
        ResponseEntity entity = sampleUserService.saveUser(userRequestDTO)

        then: "se espera la siguiente información"
        assert entity != null
        assert entity.getBody() instanceof UserResponseDTO
        assert entity.getStatusCode() == HttpStatus.OK
    }

    public void "guardar usuario con mail mal digitado"() {
        given: "una solicitud de usuario"

        List<PhoneDTO> phoneDTOList = new LinkedList<>()
        phoneDTOList.add(phoneDTOSample)

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .email("eliseohh@gmailcom")
                .name("Eliseo Henriquez")
                .password("Anj1G@code12")
                .phones(phoneDTOList)
                .build()

        and: "un servicio"
        UserService sampleUserService = new UserServiceImpl(userRepository, passwordEncoder)

        when: "cuando se guarda un usuario"
        ResponseEntity entity = sampleUserService.saveUser(userRequestDTO)

        then: "se espera la siguiente información"
        assert entity != null
        assert entity.getBody() instanceof MessageDTO
        assert entity.getStatusCode() == HttpStatus.BAD_REQUEST
        assert ((MessageDTO) entity.getBody()).getMensaje() == "Correo invalido"

    }

    public void "guardar usuario con password mal digitada"() {
        given: "una solicitud de usuario"


        List<PhoneDTO> phoneDTOList = new LinkedList<>()
        phoneDTOList.add(phoneDTOSample)

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .email("eliseohh@gmail.com")
                .name("Eliseo Henriquez")
                .password("hola")
                .phones(phoneDTOList)
                .build()

        and: "un servicio"
        UserService sampleUserService = new UserServiceImpl(userRepository, passwordEncoder)

        when: "cuando se guarda un usuario"
        ResponseEntity entity = sampleUserService.saveUser(userRequestDTO)

        then: "se espera la siguiente información"
        assert entity != null
        assert entity.getBody() instanceof MessageDTO
        assert entity.getStatusCode() == HttpStatus.BAD_REQUEST
        assert ((MessageDTO) entity.getBody()).getMensaje() == "Contrasenha no ad hoc con lo requerido"
    }

    public void "guardar usuario con correo ya registrado"() {
        given: "una solicitud de usuario"

        List<PhoneDTO> phoneDTOList = new LinkedList<>()
        phoneDTOList.add(phoneDTOSample)

        UserRequestDTO userRequestDTO = UserRequestDTO.builder()
                .email("eliseohh@gmail.com")
                .name("Eliseo Henriquez")
                .password("Anj1G@de12")
                .phones(phoneDTOList)
                .build()

        and: "el repositorio del servicio retornará la entidad ya formada"
        UserService sampleUserService = new UserServiceImpl(userRepository, passwordEncoder)
        passwordEncoder.encode(_ as String) >> Mockito.anyString()
        userRepository.existsByEmail(_ as String) >> true

        when: "se guarda info del usuario"
        ResponseEntity entity = sampleUserService.saveUser(userRequestDTO)

        then: "se espera la siguiente información"
        assert entity != null
        assert entity.getBody() instanceof MessageDTO
        assert entity.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY
        assert ((MessageDTO) entity.getBody()).getMensaje() == "El correo ya registrado"
    }
}
