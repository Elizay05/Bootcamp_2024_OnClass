package com.example.bootcamp2024onclass.configuration;

import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.example.bootcamp2024onclass.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.example.bootcamp2024onclass.domain.api.ITechnologyServicePort;
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

    @Test
    @DisplayName("When_BeanConfiguration_Expect_PortsToBeCreatedSuccessfully")
    void testBeanConfiguration() {
        BeanConfiguration beanConfiguration = new BeanConfiguration(technologyRepository, technologyEntityMapper);

        ITechnologyPersistencePort persistencePort = beanConfiguration.technologyPersistencePort();
        assertNotNull(persistencePort);

        ITechnologyServicePort servicePort = beanConfiguration.technologyServicePort();
        assertNotNull(servicePort);
    }
}
