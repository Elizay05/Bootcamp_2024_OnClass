package com.example.bootcamp2024onclass.domain.spi;

import com.example.bootcamp2024onclass.domain.model.Capacity;

import java.util.List;

public interface ICapacityPersistencePort {
    Capacity saveCapacity(Capacity capacity);

    List<Capacity> getAllCapacities(Integer page, Integer size, boolean orderByName, boolean isAscending);
}
