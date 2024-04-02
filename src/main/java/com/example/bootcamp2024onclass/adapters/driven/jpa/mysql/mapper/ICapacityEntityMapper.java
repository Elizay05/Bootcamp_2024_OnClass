package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapacityEntityMapper {

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "technologies", source = "entity.technologies")
    Capacity toModel(CapacityEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "technologies", source = "domain.technologies")
    CapacityEntity toEntity(Capacity domain);

    List<Capacity> toModelList(List<CapacityEntity> capacityEntities);
}
