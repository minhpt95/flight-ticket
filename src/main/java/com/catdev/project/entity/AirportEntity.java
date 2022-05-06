package com.catdev.project.entity;

import com.catdev.project.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "airport")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirportEntity extends DateTimeEntity {
    @Id
    @Column(nullable = false,length = 3)
    private String airportCode;

    @Column
    private String name;

    @Column
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code")
    private CountryEntity countryEntity;
}
