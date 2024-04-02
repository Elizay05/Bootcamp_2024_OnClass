package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddTechnologyRequest;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ITechnologyRequestMapperImplTest {

    private final ITechnologyRequestMapperImpl mapper = new ITechnologyRequestMapperImpl();

    @Test
    @DisplayName("When_AddTechnologyRequestMappedToTechnology_Expect_SuccessfulMapping")
    void testAddRequestToTechnology() {
        AddTechnologyRequest validRequest = new AddTechnologyRequest("Java", "Programming language");
        Technology mappedTechnology = mapper.addRequestToTechnology(validRequest);
        assertNotNull(mappedTechnology);
        assertNull(mappedTechnology.getId());
        assertEquals(validRequest.getName(), mappedTechnology.getName());
        assertEquals(validRequest.getDescription(), mappedTechnology.getDescription());

        Technology nullMappedTechnology = mapper.addRequestToTechnology(null);
        assertNull(nullMappedTechnology);
    }
}
