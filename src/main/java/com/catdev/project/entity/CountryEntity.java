package com.catdev.project.entity;

import com.catdev.project.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "country")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryEntity extends DateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(updatable = false, nullable = false,length = 2)
    private String countryCode;

    @Column(length = 45)
    private String name;

    @OneToMany(fetch = FetchType.LAZY,cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    },mappedBy = "countryEntity")
    private Set<ClientEntity> clientEntities;

    @OneToMany(fetch = FetchType.LAZY,cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    },mappedBy = "countryEntity")
    private Set<AirportEntity> airportEntities;
}
