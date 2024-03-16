package com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITechnologyEntityMapper {
    Technology toModel(TechnologyEntity technologyEntity);

    TechnologyEntity toEntity(Technology technology);

    List<Technology> toModelList(List<TechnologyEntity> technologyEntities);
}
