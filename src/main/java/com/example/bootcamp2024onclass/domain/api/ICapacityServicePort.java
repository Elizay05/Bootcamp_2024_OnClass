package com.example.bootcamp2024onclass.domain.api;

import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.CustomPage;
import com.example.bootcamp2024onclass.domain.model.PaginationCriteria;

import java.util.List;

public interface ICapacityServicePort {
    Capacity saveCapacity(Capacity capacity);

    CustomPage<Capacity> getAllCapacities(PaginationCriteria criteria);

    List<Capacity> getTotalBodyCapacities();
}
