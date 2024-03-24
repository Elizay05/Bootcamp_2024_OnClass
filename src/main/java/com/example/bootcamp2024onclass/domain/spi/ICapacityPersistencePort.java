package com.example.bootcamp2024onclass.domain.spi;

import com.example.bootcamp2024onclass.domain.model.Capacity;

import java.util.Optional;

public interface ICapacityPersistencePort {
    Capacity saveCapacity(Capacity capacity);
}
