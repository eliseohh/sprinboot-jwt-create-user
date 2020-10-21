package com.eliseo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
    private List<PhoneDTO> phones;
}
