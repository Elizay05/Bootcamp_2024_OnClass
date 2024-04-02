package com.example.bootcamp2024onclass.adapters.driving.http.dto.response;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampCapacityResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampResponse;
import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityTechnologyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BootcampResponseTest {

    @Test
    @DisplayName("When_ConstructorWithValidArguments_Expect_Success")
    void constructor_WithValidArguments_Success() {
        Long expectedId = 1L;
        String expectedName = "Bootcamp";
        String expectedDescription = "Description";

        CapacityTechnologyResponse tech1 = new CapacityTechnologyResponse(1L, "Tech1");
        CapacityTechnologyResponse tech2 = new CapacityTechnologyResponse(2L, "Tech2");
        CapacityTechnologyResponse tech3 = new CapacityTechnologyResponse(3L, "Tech3");
        List<CapacityTechnologyResponse> technologies = Arrays.asList(tech1, tech2, tech3);

        List<BootcampCapacityResponse> expectedCapacityResponses = Arrays.asList(
                new BootcampCapacityResponse(1L, "Capacity 1", technologies),
                new BootcampCapacityResponse(2L, "Capacity 2", technologies),
                new BootcampCapacityResponse(3L, "Capacity 3", technologies)
        );

        BootcampResponse bootcampResponse = new BootcampResponse(expectedId, expectedName, expectedDescription, expectedCapacityResponses);

        assertThat(bootcampResponse).isNotNull();
        assertThat(bootcampResponse.getId()).isEqualTo(expectedId);
        assertThat(bootcampResponse.getName()).isEqualTo(expectedName);
        assertThat(bootcampResponse.getDescription()).isEqualTo(expectedDescription);
        assertThat(bootcampResponse.getCapacities()).isEqualTo(expectedCapacityResponses);
    }
}
