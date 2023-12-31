package com.example.FlightAppDemo.Controllers;

import com.example.FlightAppDemo.DTO.FlightDTO;
import com.example.FlightAppDemo.DTO.SearchDTO;
import com.example.FlightAppDemo.Entities.Flight;
import com.example.FlightAppDemo.Responses.FlightResponse;
import com.example.FlightAppDemo.Services.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/Flights")
public class FlightController {
    private final FlightsService flightService;

    @Autowired
    public FlightController(FlightsService _flightService) {
        this.flightService = _flightService;
    }

    @GetMapping
    public List<FlightResponse> GetAll(){
        return flightService.getAll();
    }

    @GetMapping("/get_by_id/")
    public FlightResponse GetByID(@RequestParam("code") String id){
        return flightService.getByID(id);
    }

    @GetMapping("/get_by_place_of_departure/")
    public List<FlightResponse> GetByPlaceOfDeparture(@RequestParam("departure") String departure){
        return flightService.getByPlaceOfDeparture(departure);
    }

    @GetMapping("/get_by_destination/")
    public List<FlightResponse> GetByDestination(@RequestParam("destination") String destination){
        return flightService.getByDestination(destination);
    }

    @GetMapping("/search")
    public List<FlightResponse> SearchFlights(@RequestBody SearchDTO searchDto) {
        List<FlightResponse> flights;
        if (searchDto.getReturnDate() == null) {
            // Single-way flight search
            flights = flightService.searchSingleWayFlight(searchDto.getDepartureLocation(), searchDto.getDestination(), searchDto.getDepartureDate());
        } else {
            // Round-trip flight search
            flights = flightService.searchRoundTripFlight(searchDto.getDepartureLocation(), searchDto.getDestination(), searchDto.getDepartureDate(), searchDto.getReturnDate());
        }
        return flights;
    }

    @PostMapping
    public boolean CreateFlight(@RequestBody FlightDTO new_flight){
        return flightService.create(new_flight);
    }

    @DeleteMapping("/{id}")
    public boolean DeleteFlight(@RequestParam("id") String id){
        return flightService.delete(id);
    }

    @PutMapping("/update")
    public boolean UpdateFlight( @RequestBody Flight new_flight){
        return flightService.updateFlight(new_flight.getFlight_number(), new_flight);
    }
}
