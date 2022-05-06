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
@Table(name = "aircraft_manufacturer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AircraftManufacturerEntity extends DateTimeEntity {

    @Id
    @Column
    private Long aircraftManufacturerId;

    @Column
    private String name;

    @OneToMany(
            cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
        },
            fetch = FetchType.LAZY,
            mappedBy = "aircraftManufacturerEntity"
    )
    private Set<AircraftEntity> aircraftEntities;
}
