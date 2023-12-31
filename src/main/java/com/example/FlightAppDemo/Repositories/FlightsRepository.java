package com.example.FlightAppDemo.Repositories;


import com.example.FlightAppDemo.DTO.FlightDTO;
import com.example.FlightAppDemo.Entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface FlightsRepository extends JpaRepository<Flight, String> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO \"flights\" (id, place_of_departure, destination, departure_time, arrival_time, price) VALUES \n" +
            "(:#{#flightDto.flight_number}, " +
            "(SELECT id from \"airports\" WHERE city = :#{#flightDto.place_of_departure}), " +
            "(SELECT id from \"airports\" WHERE city = :#{#flightDto.destination}), " +
            ":#{#flightDto.departure_time}, " +
            ":#{#flightDto.arrival_time}, " +
            ":#{#flightDto.price});", nativeQuery = true)
    int insertFlight(@Param("flightDto") FlightDTO flightDto);

    @Query("SELECT f FROM Flight f WHERE f.place_of_departure.city = ?1")
    List<Flight> findByDeparture(String departure);

    @Query("SELECT f FROM Flight f WHERE f.destination.city = ?1")
    List<Flight> findByDestination(String destination);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE \"flights\" SET " +
            "place_of_departure = (SELECT id from \"airports\" WHERE city = :#{#flightDto.place_of_departure}), " +
            "destination = (SELECT id from \"airports\" WHERE city = :#{#flightDto.destination}), " +
            "departure_time = :#{#flightDto.departure_time}, " +
            "arrival_time = :#{#flightDto.arrival_time}, " +
            "price = :#{#flightDto.price} " +
            "WHERE id = :#{#flightDto.flight_number} ", nativeQuery = true)
    int updateBook(@Param("flightDto") FlightDTO flightDto);

    @Query("SELECT f FROM Flight f WHERE f.place_of_departure.city = ?1 AND f.destination.city = ?2 AND f.departure_time >= ?3 AND f.departure_time < ?4")
    List<Flight> findByDepartureLocationAndDestinationAndDepartureDate(String departureLocation, String destination, LocalDateTime startOfDay, LocalDateTime startOfNextDay);
    //select * from flights where destination = 'b12fb815-885d-4fa8-9661-34bc69d2dafd' AND place_of_departure = 'f886f3ba-371e-43b4-bc16-0b0a5500a2a4' AND departure_time >= '2023-12-15T00:00' AND departure_time < '2023-12-16T00:00';
}
