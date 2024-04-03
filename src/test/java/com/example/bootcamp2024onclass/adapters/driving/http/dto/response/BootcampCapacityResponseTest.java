package com.example.bootcamp2024onclass.adapters.driving.http.dto.response;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampCapacityResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityTechnologyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BootcampCapacityResponseTest {

    @Test
    @DisplayName("Constructor with Valid Arguments - Success")
    void constructor_WithValidArguments_Success() {
        Long id = 1L;
        String name = "CapacityName";

        CapacityTechnologyResponse tech1 = new CapacityTechnologyResponse(1L, "Tech1");
        CapacityTechnologyResponse tech2 = new CapacityTechnologyResponse(2L, "Tech2");
        CapacityTechnologyResponse tech3 = new CapacityTechnologyResponse(3L, "Tech3");
        List<CapacityTechnologyResponse> technologies = Arrays.asList(tech1, tech2, tech3);

        BootcampCapacityResponse capacityResponse = new BootcampCapacityResponse(id, name, technologies);

        assertNotNull(capacityResponse);
        assertEquals(id, capacityResponse.getId());
        assertEquals(name, capacityResponse.getName());
        assertEquals(technologies, capacityResponse.getTechnologies());
    }

    @Test
    @DisplayName("Getter and Setter Test")
    void testGetterAndSetter() {
        CapacityTechnologyResponse tech1 = new CapacityTechnologyResponse(1L, "Tech1");
        CapacityTechnologyResponse tech2 = new CapacityTechnologyResponse(2L, "Tech2");
        CapacityTechnologyResponse tech3 = new CapacityTechnologyResponse(3L, "Tech3");
        List<CapacityTechnologyResponse> technologies = Arrays.asList(tech1, tech2, tech3);

        BootcampCapacityResponse capacityResponse = new BootcampCapacityResponse(1L, "testName", technologies);

        assertEquals(1L, capacityResponse.getId());
        assertEquals("testName", capacityResponse.getName());
    }
}
