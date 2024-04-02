package com.example.bootcamp2024onclass.configuration;


import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ICapacityEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ICapacityRepository;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.bootcamp2024onclass.domain.api.ICapacityServicePort;
import com.example.bootcamp2024onclass.domain.api.ITechnologyServicePort;
import com.example.bootcamp2024onclass.domain.spi.ICapacityPersistencePort;
import com.example.bootcamp2024onclass.domain.spi.ITechnologyPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BeanConfigurationTest {

    @MockBean
    private ITechnologyRepository technologyRepository;

    @MockBean
    private ITechnologyEntityMapper technologyEntityMapper;

    @MockBean
    private ICapacityRepository capacityRepository;

    @MockBean
    private ICapacityEntityMapper capacityEntityMapper;

    @Test
    @DisplayName("When_BeanConfiguration_Expect_PortsToBeCreatedSuccessfully")
    void testBeanConfiguration() {
        BeanConfiguration beanConfiguration = new BeanConfiguration(
                technologyRepository, technologyEntityMapper, capacityRepository, capacityEntityMapper);

        ITechnologyPersistencePort technologyPersistencePort = beanConfiguration.technologyPersistencePort();
        assertNotNull(technologyPersistencePort);

        ITechnologyServicePort technologyServicePort = beanConfiguration.technologyServicePort();
        assertNotNull(technologyServicePort);

        ICapacityPersistencePort capacityPersistencePort = beanConfiguration.capacityPersistencePort();
        assertNotNull(capacityPersistencePort);

        ICapacityServicePort capacityServicePort = beanConfiguration.capacityServicePort();
        assertNotNull(capacityServicePort);
    }
}
