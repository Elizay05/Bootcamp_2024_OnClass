package com.example.Bootcamp_2024_OnClass.configuration;

import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.adapter.TechnologyAdapter;
import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.example.Bootcamp_2024_OnClass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.Bootcamp_2024_OnClass.domain.api.ITechnologyServicePort;
import com.example.Bootcamp_2024_OnClass.domain.api.usecase.TechnologyUseCase;
import com.example.Bootcamp_2024_OnClass.domain.spi.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ITechnologyRepository technologyRepository;
    private final ITechnologyEntityMapper technologyEntityMapper;

    @Bean
    public ITechnologyPersistencePort technologyPersistencePort() {
        return new TechnologyAdapter(technologyEntityMapper, technologyRepository);
    }
    @Bean
    public ITechnologyServicePort technologyServicePort() {
        return new TechnologyUseCase(technologyPersistencePort());
    }
}

