package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityResponse;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICapacityResponseMapper {
    @Mapping(source = "technologies", target = "capacityTechnologyResponses")
    CapacityResponse toCapacityResponse(Capacity capacity);
    /*
    @Mapping(target = "id", source = "capacity.id")
    @Mapping(target = "name", source = "capacity.name")
    @Mapping(target = "description", source = "capacity.description")
    @Mapping(target = "capacityTechnologyResponses", source = "capacity.technologies")
    CapacityResponse toCapacityResponse(Capacity capacity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CapacityTechnologyResponse toTechnologyResponse(Technology technology);

     */
}
