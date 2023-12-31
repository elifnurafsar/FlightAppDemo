package com.example.FlightAppDemo.Services;

import com.example.FlightAppDemo.DTO.AirportDTO;
import com.example.FlightAppDemo.Entities.Airport;
import com.example.FlightAppDemo.Mappers.AirportMapper;
import com.example.FlightAppDemo.Repositories.AirportRepository;
import com.example.FlightAppDemo.Responses.AirportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AirportService {
    //private ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    private final AirportRepository airportsRepository;

    @Autowired
    public AirportService(AirportRepository _airportsRepository) {
        this.airportsRepository = _airportsRepository;
    }

    public boolean updateAirport(UUID id, String city) {
        int a = airportsRepository.updateAirport(id, city);
        return a > 0;
    }

    public boolean delete(String id) {
        UUID _id = UUID.fromString(id);
        airportsRepository.deleteById(_id);
        return airportsRepository.findById(_id).isEmpty();
    }

    public boolean create(AirportDTO airportDTO) {
        int a = airportsRepository.insertAirport(airportDTO.getCity());
        return a > 0;
    }

    public List<AirportResponse> filter(String city) {
        List<Airport> airportsList = airportsRepository.filter(city);
        return AirportMapper.INSTANCE.entityListToResponseList(airportsList);
    }

    public AirportResponse getByID(String id) {
        UUID _id = UUID.fromString(id);
        Optional<Airport> airports = airportsRepository.findById(_id);
        if(!airports.isEmpty())
            return AirportMapper.INSTANCE.entityToResponse(airports.get());
        else
            return null;
    }

    public List<AirportResponse> getAll() {
        List<Airport> airportsList = airportsRepository.findAll();
        return AirportMapper.INSTANCE.entityListToResponseList(airportsList);
    }
}
