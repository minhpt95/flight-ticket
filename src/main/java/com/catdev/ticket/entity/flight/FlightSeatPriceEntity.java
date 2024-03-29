package com.catdev.ticket.entity.flight;

import com.catdev.ticket.entity.aircraft.AircraftSeatEntity;
import com.catdev.ticket.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "flight_seat_price")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightSeatPriceEntity extends DateTimeEntity {

    @EmbeddedId
    private FlightSeatPriceEntityId flightSeatPriceEntityId;

    @Column
    public BigDecimal price;

    @Column
    private String currency;
}

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class FlightSeatPriceEntityId implements Serializable {

    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private FlightEntity flight;


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "seat_id", insertable = false, updatable = false),
            @JoinColumn(name = "aircraft_id", insertable = false, updatable = false)
    })
    private AircraftSeatEntity aircraftSeat;
}
