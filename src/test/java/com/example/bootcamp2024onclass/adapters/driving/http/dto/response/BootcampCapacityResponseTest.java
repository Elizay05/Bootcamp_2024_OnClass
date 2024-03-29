package com.example.bootcamp2024onclass.adapters.driving.http.dto.response;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampCapacityResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BootcampCapacityResponseTest {

    @Test
    @DisplayName("Constructor with Valid Arguments - Success")
    void constructor_WithValidArguments_Success() {
        Long id = 1L;
        String name = "CapacityName";

        BootcampCapacityResponse capacityResponse = new BootcampCapacityResponse(id, name);

        assertNotNull(capacityResponse);
        assertEquals(id, capacityResponse.getId());
        assertEquals(name, capacityResponse.getName());
    }

    @Test
    @DisplayName("Getter and Setter Test")
    void testGetterAndSetter() {
        BootcampCapacityResponse capacityResponse = new BootcampCapacityResponse(1L, "testName");

        assertEquals(1L, capacityResponse.getId());
        assertEquals("testName", capacityResponse.getName());
    }
}
