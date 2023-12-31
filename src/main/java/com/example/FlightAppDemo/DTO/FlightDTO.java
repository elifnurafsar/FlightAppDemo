package com.example.FlightAppDemo.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class FlightDTO {
    private String flight_number;
    private String place_of_departure;
    private String destination;
    private Timestamp departure_time;
    private Timestamp arrival_time;
    private double price;
}
