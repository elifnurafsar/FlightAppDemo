package com.example.FlightAppDemo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;

@Entity
@Table(name = "flights", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String flight_number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place_of_departure", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Airport place_of_departure;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Airport destination;

    @Column(name = "departure_time", nullable = false)
    private Timestamp departure_time;
    @Column(name = "arrival_time",  nullable = false)
    private Timestamp arrival_time;
    @Column(name = "price", nullable = false)
    private double price;

    public String getPlaceOfDeparture() { return this.place_of_departure.getCity(); }

    public String getDestination() { return this.destination.getCity(); }
}
