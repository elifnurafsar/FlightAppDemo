package com.example.FlightAppDemo.Mappers;

import com.example.FlightAppDemo.Entities.Flight;
import com.example.FlightAppDemo.Responses.FlightResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import java.util.List;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FlightMapper {
    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    @Mappings({
            @Mapping(source = "flight", target = "place_of_departure", qualifiedByName = "getPlaceOfDeparture"),
            @Mapping(source = "flight", target = "destination", qualifiedByName = "getDestination")
    })
    FlightResponse entityToResponse(Flight flight);

    List<FlightResponse> entityListToResponseList(List<Flight> flights);

    @Named("getPlaceOfDeparture")
    default String getPlaceOfDeparture(Flight flight) {
        return flight.getPlaceOfDeparture();
    }

    @Named("getDestination")
    default String getDestination(Flight flight) {
        return flight.getDestination();
    }
}
