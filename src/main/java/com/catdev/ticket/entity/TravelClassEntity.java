package com.catdev.ticket.entity;

import com.catdev.ticket.entity.aircraft.AircraftSeatEntity;
import com.catdev.ticket.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "travel_class")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravelClassEntity extends DateTimeEntity {

    @Id
    @Column
    private Long travelClassId;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            },
            mappedBy = "travelClass"
    )
    private Set<AircraftSeatEntity> aircraftSeatEntities;
}
