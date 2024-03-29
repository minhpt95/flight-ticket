package com.catdev.ticket.entity;

import com.catdev.ticket.entity.common.CommonEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends CommonEntity {
    @Column
    private Long userTelegramId;
    @Column
    private String name;
    @Column(unique = true,nullable = false)
    private String email;
    @Column
    private String password;
    @Column(unique = true,nullable = false)
    private String identityCard;
    @Column
    private String phoneNumber1;
    @Column
    private String phoneNumber2;
    @Column
    private String currentAddress;
    @Column
    private String permanentAddress;
    @Column
    private String description;
    @Column
    private String accessToken;
    @Column
    private boolean tokenStatus;
    @Column
    private boolean isEnabled;
    @Column
    private String transactionPassword;
}
