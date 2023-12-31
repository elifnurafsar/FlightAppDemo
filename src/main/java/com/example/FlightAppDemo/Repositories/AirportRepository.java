package com.example.FlightAppDemo.Repositories;

import com.example.FlightAppDemo.Entities.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface AirportRepository extends JpaRepository<Airport, UUID> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO \"airports\" (city) VALUES \n" +
            "(?1);", nativeQuery = true)
    int insertAirport(String city);

    @Query(value = "SELECT * FROM \"airports\" WHERE city ilike %?1%", nativeQuery = true)
    List<Airport> filter(String city);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Airport a SET a.city = ?2 WHERE a.id = ?1")
    int updateAirport(UUID id, String city);
}
