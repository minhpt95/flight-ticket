package com.catdev.ticket.entity.aircraft;

import com.catdev.ticket.entity.common.DateTimeEntity;
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
    @Id
    @Column
    private Long aircraftId;

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
