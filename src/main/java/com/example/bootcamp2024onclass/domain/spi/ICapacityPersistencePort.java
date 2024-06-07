package com.example.bootcamp2024onclass.domain.spi;

import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;

import java.util.List;

public interface ICapacityPersistencePort {
    Capacity saveCapacity(Capacity capacity);

    CustomPage<Capacity> getAllCapacities(PaginationCriteria criteria);

    List<Capacity> getTotalBodyCapacities();
}
