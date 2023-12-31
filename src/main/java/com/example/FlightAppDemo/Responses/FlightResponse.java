package com.example.FlightAppDemo.Responses;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FlightResponse {
    private String flight_number;
    private String place_of_departure;
    private String destination;
    private Timestamp departure_time;
    private Timestamp arrival_time;
    private double price;
}
