package com.catdev.project.entity.flight;

import com.catdev.project.entity.common.DateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flight")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightEntity extends DateTimeEntity {
    @Id
    @Column
    private Long flightId;



}
