package com.catdev.project.entity.flight;

import com.catdev.project.entity.aircraft.AircraftInstanceEntity;
import com.catdev.project.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "flight_aircraft_instance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightAircraftInstance extends DateTimeEntity {

    @EmbeddedId
    private FlightAircraftId flightAircraftId;

}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
class FlightAircraftId implements Serializable {

    @Serial
    private static final long serialVersionUID = 5490723779475354932L;

    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aircraft_instance_id")
    private AircraftInstanceEntity aircraftInstance;
}
