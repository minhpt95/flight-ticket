package com.catdev.project.entity.aircraft;

import com.catdev.project.entity.TravelClassEntity;
import com.catdev.project.entity.common.CommonEntity;
import com.catdev.project.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;


@Entity
@Table(name = "aircraft_seat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AircraftSeatEntity extends DateTimeEntity {
    @EmbeddedId
    private AircraftSeatId aircraftSeatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_class_id")
    private TravelClassEntity travelClass;
}

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class AircraftSeatId implements Serializable {
    @Serial
    private static final long serialVersionUID = -4742105540713056892L;

    private Long seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id")
    private AircraftEntity aircraft;
}

