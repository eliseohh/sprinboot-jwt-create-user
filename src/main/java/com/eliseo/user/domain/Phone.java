package com.eliseo.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table
@NoArgsConstructor
public class Phone {

    @Id
    @GeneratedValue
    private String id;

    @Column
    private String number;

    @Column
    private String countryCode;

    @Column
    private String cityCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
