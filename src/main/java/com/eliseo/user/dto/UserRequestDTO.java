package com.eliseo.user.dto;

import lombok.*;

import java.util.List;

@Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
    private List<PhoneDTO> phones;
}
