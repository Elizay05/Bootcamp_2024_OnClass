package com.example.bootcamp2024onclass.domain.spi;

import com.example.bootcamp2024onclass.domain.model.VersionBootcamp;

public interface IVersionBootcampPersistencePort {

    VersionBootcamp saveVersionBootcamp(VersionBootcamp versionBootcamp);
}
