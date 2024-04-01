package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.api.IBootcampServicePort;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.spi.IBootcampPersistencePort;

import java.util.List;

public class BootcampUseCase implements IBootcampServicePort {

    private final IBootcampPersistencePort bootcampPersistencePort;

    public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort) {
        this.bootcampPersistencePort = bootcampPersistencePort;
    }

    @Override
    public Bootcamp saveBootcamp(Bootcamp bootcamp) {
        return bootcampPersistencePort.saveBootcamp(bootcamp);
    }

    @Override
    public List<Bootcamp> getAllBootcamps(Integer page, Integer size, boolean isOrderByName, boolean isAscending) {
        return bootcampPersistencePort.getAllBootcamps(page, size, isOrderByName, isAscending);
    }
}
