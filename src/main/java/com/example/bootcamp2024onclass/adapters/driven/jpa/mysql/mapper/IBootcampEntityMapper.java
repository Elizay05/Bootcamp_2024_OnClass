package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBootcampEntityMapper {

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "capacities", source = "entity.capacities")
    Bootcamp toModel(BootcampEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "capacities", source = "domain.capacities")
    BootcampEntity toEntity(Bootcamp domain);

}
