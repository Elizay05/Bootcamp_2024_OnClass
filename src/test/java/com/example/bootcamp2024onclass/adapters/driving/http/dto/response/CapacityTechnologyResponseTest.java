package com.example.bootcamp2024onclass.adapters.driving.http.dto.response;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityTechnologyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class CapacityTechnologyResponseTest {
    @Test
    @DisplayName("Constructor with Valid Arguments - Success")
    void constructor_WithValidArguments_Success() {
        Long id = 1L;
        String name = "TechnologyName";

        CapacityTechnologyResponse technologyResponse = new CapacityTechnologyResponse(id, name);

        assertNotNull(technologyResponse);
        assertEquals(id, technologyResponse.getId());
        assertEquals(name, technologyResponse.getName());
    }

    @Test
    @DisplayName("Getter and Setter Test")
    void testGetterAndSetter() {
        // Arrange
        CapacityTechnologyResponse technologyResponse = new CapacityTechnologyResponse(1L, "testName");

        // Assert
        assertEquals(1L, technologyResponse.getId());
        assertEquals("testName", technologyResponse.getName());
    }
}
