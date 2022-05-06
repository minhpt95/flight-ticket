package com.catdev.project.entity;

import com.catdev.project.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity extends DateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(updatable = false, nullable = false)
    private Long clientId;

    @Column
    private String firstName;

    @Column
    private String middleName;

    @Column
    private String lastName;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String passport;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code")
    private CountryEntity countryEntity;


}
