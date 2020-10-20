package com.eliseo.user.dto;

import lombok.*;

@Builder
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {
    private String number;
    private String cityCode;
    private String countryCode;
}
