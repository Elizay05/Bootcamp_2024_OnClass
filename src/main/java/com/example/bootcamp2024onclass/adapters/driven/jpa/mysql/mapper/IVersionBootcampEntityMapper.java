package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.VersionBootcampEntity;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IVersionBootcampEntityMapper {
    @Mapping(source = "bootcamp.id", target = "bootcampId")
    @Mapping(source = "bootcamp.name", target = "bootcampName")
    VersionBootcamp toModel(VersionBootcampEntity versionBootcampEntity);
    VersionBootcampEntity toEntity(VersionBootcamp versionBootcamp);

    List<VersionBootcamp> toModelList(List<VersionBootcampEntity> versionBootcampEntities);
}
