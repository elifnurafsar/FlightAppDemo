package com.example.FlightAppDemo.Controllers;

import com.example.FlightAppDemo.DTO.AirportDTO;
import com.example.FlightAppDemo.Entities.Airport;
import com.example.FlightAppDemo.Responses.AirportResponse;
import com.example.FlightAppDemo.Services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Airports")
public class AirportController {
    private final AirportService airportsService;

    @Autowired
    public AirportController(AirportService airportsService) {
        this.airportsService = airportsService;
    }

    @GetMapping
    public List<AirportResponse> GetAll(){
        return airportsService.getAll();
    }

    @GetMapping("/get_by_id/")
    public AirportResponse GetByID(@RequestParam("id") String id){
        return airportsService.getByID(id);
    }

    @GetMapping("/filter/")
    public List<AirportResponse> Filter(@RequestParam("filter") String city){
        return airportsService.filter(city);
    }

    @PostMapping
    public boolean CreateAirport(@RequestBody AirportDTO airportDTO){
        return airportsService.create(airportDTO);
    }

    @DeleteMapping("/{id}")
    public boolean DeleteAirport(@RequestParam("id") String id){
        return airportsService.delete(id);
    }

    @PutMapping("/update")
    public boolean UpdateAirport( @RequestBody Airport airport){
        return airportsService.updateAirport(airport.getId(), airport.getCity());
    }
}
