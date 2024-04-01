package com.example.bootcamp2024onclass.adapters.driving.http.dto.response;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.TechnologyResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TechnologyResponseTest {
    @Test
    @DisplayName("When_ConstructorWithValidArguments_Expect_Success")
    void constructor_WithValidArguments_Success() {
        Long expectedId = 1L;
        String expectedName = "Technology";
        String expectedDescription = "Description";

        TechnologyResponse technologyResponse = new TechnologyResponse(expectedId, expectedName, expectedDescription);

        assertThat(technologyResponse).isNotNull();
        assertThat(technologyResponse.getId()).isEqualTo(expectedId);
        assertThat(technologyResponse.getName()).isEqualTo(expectedName);
        assertThat(technologyResponse.getDescription()).isEqualTo(expectedDescription);
    }
}
