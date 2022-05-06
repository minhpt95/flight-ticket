package com.catdev.project.entity.aircraft;

import com.catdev.project.entity.common.CommonEntity;
import com.catdev.project.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "aircraft_instance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AircraftInstanceEntity extends DateTimeEntity {
    @Id
    @Column
    private Long aircraftInstanceId;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
        }
    )
    @JoinColumn(name = "aircraft_id")
    private AircraftEntity aircraft;
}
