package com.eliseo.user.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public class UserResponseDTO {
    private String Id;
    private Date created;
    private Date modified;
    private Date lastLogin;
    private String token;
    private Boolean isActive;
}
