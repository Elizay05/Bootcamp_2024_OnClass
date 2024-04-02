package com.example.bootcamp2024onclass.configuration;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter.BootcampAdapter;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter.CapacityAdapter;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.adapter.TechnologyAdapter;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.IBootcampEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.bootcamp2024onclass.domain.api.IBootcampServicePort;
import com.example.bootcamp2024onclass.domain.api.ICapacityServicePort;
import com.example.bootcamp2024onclass.domain.api.ITechnologyServicePort;
import com.example.bootcamp2024onclass.domain.api.usecase.BootcampUseCase;
import com.example.bootcamp2024onclass.domain.api.usecase.CapacityUseCase;
import com.example.bootcamp2024onclass.domain.api.usecase.TechnologyUseCase;
import com.example.bootcamp2024onclass.domain.spi.IBootcampPersistencePort;
import com.example.bootcamp2024onclass.domain.spi.ICapacityPersistencePort;
import com.example.bootcamp2024onclass.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ITechnologyRepository technologyRepository;
    private final ITechnologyEntityMapper technologyEntityMapper;
    private final ICapacityRepository capacityRepository;
    private final ICapacityEntityMapper capacityEntityMapper;
    private final IBootcampRepository bootcampRepository;
    private final IBootcampEntityMapper bootcampEntityMapper;

    @Bean
    public ITechnologyPersistencePort technologyPersistencePort() {
        return new TechnologyAdapter(technologyEntityMapper, technologyRepository);
    }
    @Bean
    public ITechnologyServicePort technologyServicePort() {
        return new TechnologyUseCase(technologyPersistencePort());
    }

    @Bean
    public ICapacityPersistencePort capacityPersistencePort() {
        return new CapacityAdapter(capacityRepository, capacityEntityMapper, technologyRepository);
    }

    @Bean
    public ICapacityServicePort capacityServicePort() {
        return new CapacityUseCase(capacityPersistencePort());
    }

    @Bean
    public IBootcampPersistencePort bootcampPersistencePort() {
        return new BootcampAdapter(bootcampRepository, bootcampEntityMapper, capacityRepository);
    }

    @Bean
    public IBootcampServicePort bootcampServicePort() {
        return new BootcampUseCase(bootcampPersistencePort());
    }
}

