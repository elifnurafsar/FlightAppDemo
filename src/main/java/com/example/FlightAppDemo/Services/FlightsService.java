package com.example.FlightAppDemo.Services;

import com.example.FlightAppDemo.DTO.FlightDTO;
import com.example.FlightAppDemo.Entities.Flight;
import com.example.FlightAppDemo.Mappers.FlightMapper;
import com.example.FlightAppDemo.Repositories.FlightsRepository;
import com.example.FlightAppDemo.Responses.FlightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightsService {

    //private ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    private final FlightsRepository flightsRepository;

    @Autowired
    public FlightsService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public List<FlightResponse> getAll() {
        List<Flight> flightsList = flightsRepository.findAll();
        return FlightMapper.INSTANCE.entityListToResponseList(flightsList);
    }

    public FlightResponse getByID(String id) {
        Optional<Flight> flight = flightsRepository.findById(id);
        if(!flight.isEmpty())
            return FlightMapper.INSTANCE.entityToResponse(flight.get());
        else
            return null;
    }

    public List<FlightResponse> getByPlaceOfDeparture(String departure) {
        List<Flight> flightsList = flightsRepository.findByDeparture(departure);
        return FlightMapper.INSTANCE.entityListToResponseList(flightsList);
    }

    public List<FlightResponse> getByDestination(String destination) {
        List<Flight> flightsList = flightsRepository.findByDestination(destination);
        return FlightMapper.INSTANCE.entityListToResponseList(flightsList);
    }

    public boolean create(FlightDTO flightDTO) {
        int a = flightsRepository.insertFlight(flightDTO);
        return a > 0;
    }

    public boolean delete(String id) {
        flightsRepository.deleteById(id);
        return flightsRepository.findById(id).isEmpty();
    }

    public boolean updateFlight(String id, Flight new_flight) {
        return  true;
    }

    public List<FlightResponse> searchSingleWayFlight(String departureLocation, String destination, LocalDate departureDate) {
        LocalDateTime startOfDay = departureDate.atStartOfDay();
        LocalDateTime startOfNextDay = departureDate.plusDays(1).atStartOfDay();
        List<Flight> flightsList = flightsRepository.findByDepartureLocationAndDestinationAndDepartureDate(departureLocation, destination, startOfDay, startOfNextDay);
        return FlightMapper.INSTANCE.entityListToResponseList(flightsList);
    }

    public List<FlightResponse> searchRoundTripFlight(String departureLocation, String destination, LocalDate departureDate, LocalDate returnDate) {
        LocalDateTime startOfDayDeparture = departureDate.atStartOfDay();
        LocalDateTime startOfNextDayDeparture = departureDate.plusDays(1).atStartOfDay();

        LocalDateTime startOfDayReturn = returnDate.atStartOfDay();
        LocalDateTime startOfNextDayReturn = returnDate.plusDays(1).atStartOfDay();
        // Outgoing flight
        List<Flight> outgoingFlights = flightsRepository.findByDepartureLocationAndDestinationAndDepartureDate(departureLocation, destination, startOfDayDeparture, startOfNextDayDeparture);
        List<FlightResponse> flights = FlightMapper.INSTANCE.entityListToResponseList(outgoingFlights);

        // Return flight
        List<Flight> returnFlights = flightsRepository.findByDepartureLocationAndDestinationAndDepartureDate(destination, departureLocation, startOfDayReturn, startOfNextDayReturn);
        List<FlightResponse> returnFlightsResponse = FlightMapper.INSTANCE.entityListToResponseList(returnFlights);
        flights.addAll(returnFlightsResponse);

        return flights;
    }

}
