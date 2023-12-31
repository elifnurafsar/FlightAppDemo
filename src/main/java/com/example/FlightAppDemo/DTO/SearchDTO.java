package com.example.FlightAppDemo.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SearchDTO {
    private String departureLocation;
    private String destination;
    private LocalDate departureDate;
    private LocalDate returnDate;
}
