package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.TechnologyResponse;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ITechnologyResponseMapperImplTest {

    private final ITechnologyResponseMapperImpl mapper = new ITechnologyResponseMapperImpl();

    @Test
    @DisplayName("When_TechnologyConvertedToTechnologyResponse_Expect_SuccessfulConversion")
    void testToTechnologyResponse() {
        Technology validTechnology = new Technology(1L, "Java", "Programming language");
        TechnologyResponse mappedResponse = mapper.toTechnologyResponse(validTechnology);
        assertNotNull(mappedResponse);
        assertEquals(validTechnology.getId(), mappedResponse.getId());
        assertEquals(validTechnology.getName(), mappedResponse.getName());
        assertEquals(validTechnology.getDescription(), mappedResponse.getDescription());

        TechnologyResponse nullMappedResponse = mapper.toTechnologyResponse(null);
        assertNull(nullMappedResponse);
    }

    @Test
    @DisplayName("When_TechnologyListConvertedToTechnologyResponseList_Expect_SuccessfulConversion")
    void testToTechnologyResponseList() {
        List<Technology> validTechnologyList = new ArrayList<>();
        validTechnologyList.add(new Technology(1L, "Java", "Programming language"));
        validTechnologyList.add(new Technology(2L, "Python", "High-level programming language"));
        List<TechnologyResponse> mappedResponseList = mapper.toTechnologyResponseList(validTechnologyList);
        assertNotNull(mappedResponseList);
        assertEquals(validTechnologyList.size(), mappedResponseList.size());
        for (int i = 0; i < validTechnologyList.size(); i++) {
            Technology validTech = validTechnologyList.get(i);
            TechnologyResponse mappedTech = mappedResponseList.get(i);
            assertEquals(validTech.getId(), mappedTech.getId());
            assertEquals(validTech.getName(), mappedTech.getName());
            assertEquals(validTech.getDescription(), mappedTech.getDescription());
        }

        List<TechnologyResponse> nullMappedResponseList = mapper.toTechnologyResponseList(null);
        assertNull(nullMappedResponseList);
    }
}
