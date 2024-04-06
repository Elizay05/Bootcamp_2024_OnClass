package com.example.bootcamp2024onclass.domain.api.usecase;

import com.example.bootcamp2024onclass.domain.api.IVersionBootcampServicePort;
import com.example.bootcamp2024onclass.domain.exception.StartDateAfterEndDateException;
import com.example.bootcamp2024onclass.domain.exception.StartDateBeforeCurrentDateException;
import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;
import com.example.bootcamp2024onclass.domain.spi.IVersionBootcampPersistencePort;

import java.time.LocalDate;

public class VersionBootcampUseCase implements IVersionBootcampServicePort{
    private final IVersionBootcampPersistencePort versionBootcampPersistencePort;

    public VersionBootcampUseCase(IVersionBootcampPersistencePort versionBootcampPersistencePort) {
        this.versionBootcampPersistencePort = versionBootcampPersistencePort;
    }

    @Override
    public VersionBootcamp saveVersionBootcamp(VersionBootcamp versionBootcamp) {
        LocalDate currentDate = LocalDate.now();

        if (versionBootcamp.getStartDate().isBefore(currentDate)) {
            throw new StartDateBeforeCurrentDateException();
        }

        if (versionBootcamp.getStartDate().isAfter(versionBootcamp.getEndDate())) {
            throw new StartDateAfterEndDateException();
        }

        return versionBootcampPersistencePort.saveVersionBootcamp(versionBootcamp);
    }
}
