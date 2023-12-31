package com.example.FlightAppDemo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "airports", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "city", columnDefinition = "text", nullable = false)
    private String city;
}
