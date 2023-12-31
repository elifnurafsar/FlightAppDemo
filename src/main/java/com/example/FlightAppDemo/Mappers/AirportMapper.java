package com.example.FlightAppDemo.Mappers;

import com.example.FlightAppDemo.Entities.Airport;
import com.example.FlightAppDemo.Responses.AirportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AirportMapper {
    AirportMapper INSTANCE = Mappers.getMapper(AirportMapper.class);

    AirportResponse entityToResponse(Airport airport);

    List<AirportResponse> entityListToResponseList(List<Airport> airports);
}
