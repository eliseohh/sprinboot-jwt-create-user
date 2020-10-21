package com.eliseo.user.service.impl;

import com.eliseo.user.domain.Phone;
import com.eliseo.user.domain.User;
import com.eliseo.user.dto.MessageDTO;
import com.eliseo.user.dto.UserRequestDTO;
import com.eliseo.user.dto.UserResponseDTO;
import com.eliseo.user.repository.UserRepository;
import com.eliseo.user.service.UserService;
import com.eliseo.user.utils.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    /**
     * regex validation here: https://emailregex.com/
     */
    private static final String MAIL_REGEX_VALIDATOR = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    /**
     * reference https://www.regextester.com/97402
     */
    private static final String PASSWORD_REGEX_VALIDATOR = "^(?=.*[a-z])(?=.*[A-Z]+)(?=.*\\d{2,})";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public ResponseEntity saveUser(UserRequestDTO userRequestDTO) {
        log.info("[INIT] start to saving user");
        Pattern mailPattern = Pattern.compile(MAIL_REGEX_VALIDATOR);
        Matcher mailMatcher = mailPattern.matcher(userRequestDTO.getEmail());
        Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX_VALIDATOR);
        Matcher passwordMatcher = passwordPattern.matcher(userRequestDTO.getPassword());

        if (!mailMatcher.matches()) {
            log.error("[FIN_EX] mail is invalid: {}", userRequestDTO.getEmail());
            return ResponseEntity.badRequest().body(new MessageDTO("Correo invalido"));
        }

        if (!passwordMatcher.find()) {
            log.error("[FIN_EX] password doesn't match: {}", userRequestDTO.getPassword());
            return ResponseEntity.badRequest().body(new MessageDTO("Contrasenha no ad hoc con lo requerido"));
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail())){
            //TODO: se debería retornar el email ya tomado? consultar
            log.error("[FIN_EX] mail already taken");
            return ResponseEntity.unprocessableEntity().body(new MessageDTO("El correo ya registrado"));
        }

        log.info("creating user");
        User user = User.builder()
                .email(userRequestDTO.getEmail())
                .name(userRequestDTO.getName())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .phones(userRequestDTO.getPhones()
                        .stream()
                        .map(e -> {
                            Phone phone = new Phone();
                            phone.setCityCode(e.getCityCode());
                            phone.setCountryCode(e.getCountryCode());
                            phone.setNumber(e.getNumber());

                            return phone;
                        })
                        .collect(Collectors.toList()))
                .build();

        log.info("saving user");
        userRepository.save(user);
        log.info("saving ok");

        UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                .Id(user.getId())
                .created(user.getCreated())
                .modified(user.getUpdated())
                .lastLogin(user.getLastLogin())
                .token(TokenManager.generateToken(user.getEmail()))
                .isActive(user.isActive())
                .build();

        log.info("[FIN_OK] finish to saving and returning new   user");
        return ResponseEntity.ok(userResponseDTO);
    }
}
