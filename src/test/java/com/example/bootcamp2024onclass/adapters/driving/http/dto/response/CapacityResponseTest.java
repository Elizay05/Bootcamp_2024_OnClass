package com.example.bootcamp2024onclass.adapters.driving.http.dto.response;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityTechnologyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CapacityResponseTest {
    @Test
    @DisplayName("When_ConstructorWithValidArguments_Expect_Success")
    void constructor_WithValidArguments_Success() {
        Long expectedId = 1L;
        String expectedName = "Capacity";
        String expectedDescription = "Description";
        List<CapacityTechnologyResponse> expectedTechnologyResponses = Arrays.asList(
                new CapacityTechnologyResponse(1L, "Technology 1"),
                new CapacityTechnologyResponse(2L, "Technology 2"),
                new CapacityTechnologyResponse(3L, "Technology 3")
        );

        CapacityResponse capacityResponse = new CapacityResponse(expectedId, expectedName, expectedDescription, expectedTechnologyResponses);

        assertThat(capacityResponse).isNotNull();
        assertThat(capacityResponse.getId()).isEqualTo(expectedId);
        assertThat(capacityResponse.getName()).isEqualTo(expectedName);
        assertThat(capacityResponse.getDescription()).isEqualTo(expectedDescription);
        assertThat(capacityResponse.getTechnologies()).isEqualTo(expectedTechnologyResponses);
    }
}
