package com.catdev.project.entity.aircraft;

import com.catdev.project.entity.common.CommonEntity;
import com.catdev.project.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "aircraft")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AircraftEntity extends DateTimeEntity {

    @Column
    private String model;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            }
            )
    @JoinColumn(name = "aircraft_manufacturer_id")
    private AircraftManufacturerEntity aircraftManufacturerEntity;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            },
            mappedBy = "aircraft"
    )
    private Set<AircraftInstanceEntity> aircraftInstanceEntities;
}
