package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddCapacityRequest;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface ICapacityRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "technologies", source = "technologyIds", qualifiedByName = "mapTechnologyIdsToTechnologies")
    Capacity addRequestToCapacity(AddCapacityRequest addCapacityRequest);

    @Named("mapTechnologyIdsToTechnologies")
    default List<Technology> mapTechnologyIdsToTechnologies(List<Long> technologyIds) {
        if (technologyIds == null || technologyIds.isEmpty()) {
            return Collections.emptyList();
        }
        return technologyIds.stream().map(t -> {
            return new Technology(t);
        }).toList();
    }
}
