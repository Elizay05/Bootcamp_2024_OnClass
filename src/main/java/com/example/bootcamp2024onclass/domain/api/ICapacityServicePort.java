package com.example.bootcamp2024onclass.domain.api;

import com.example.bootcamp2024onclass.domain.model.Capacity;

import java.util.List;

public interface ICapacityServicePort {
    Capacity saveCapacity(Capacity capacity);

    List<Capacity> getAllCapacities(Integer page, Integer size, boolean orderByName, boolean isAscending);
}
