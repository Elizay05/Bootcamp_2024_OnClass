package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.api.ICapacityServicePort;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.spi.ICapacityPersistencePort;

public class CapacityUseCase implements ICapacityServicePort {

    private final ICapacityPersistencePort capacityPersistencePort;

    public CapacityUseCase(ICapacityPersistencePort capacityPersistencePort) {
        this.capacityPersistencePort = capacityPersistencePort;
    }

    @Override
    public Capacity saveCapacity(Capacity capacity) {
        return capacityPersistencePort.saveCapacity(capacity);
    }
}