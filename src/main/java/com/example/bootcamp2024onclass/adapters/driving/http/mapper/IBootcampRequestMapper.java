package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddBootcampRequest;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.model.Capacity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "capacities", source = "capacities", qualifiedByName = "mapCapacityIdsToCapacities")
    Bootcamp addRequestToBootcamp(AddBootcampRequest addBootcampRequest);

    @Named("mapCapacityIdsToCapacities")
    default List<Capacity> mapCapacityIdsToCapacities(List<Long> capacityIds) {
        if (capacityIds == null || capacityIds.isEmpty()) {
            return Collections.emptyList();
        }
        return capacityIds.stream()
                .map(Capacity::new)
                .toList();
    }
}
