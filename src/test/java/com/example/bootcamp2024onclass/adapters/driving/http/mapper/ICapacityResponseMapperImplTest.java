package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.CapacityResponse;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ICapacityResponseMapperImplTest {

    private final ICapacityResponseMapperImpl mapper = new ICapacityResponseMapperImpl();

    @Test
    @DisplayName("When_CapacityConvertedToCapacityResponse_Expect_SuccessfulConversion")
    void testToCapacityResponse() {
        List<Technology> technologies = new ArrayList<>();
        technologies.add(new Technology(1L, "Java", "Programming language"));
        technologies.add(new Technology(2L, "Python", "High-level programming language"));
        technologies.add(new Technology(3L, "JavaScript", "High-level programming language"));
        Capacity capacity = new Capacity(1L, "Capacity Name", "Capacity Description", technologies);

        CapacityResponse mappedResponse = mapper.toCapacityResponse(capacity);
        assertNotNull(mappedResponse);
        assertEquals(capacity.getId(), mappedResponse.getId());
        assertEquals(capacity.getName(), mappedResponse.getName());
        assertEquals(capacity.getDescription(), mappedResponse.getDescription());
        assertEquals(technologies.size(), mappedResponse.getTechnologies().size());

        CapacityResponse nullMappedResponse = mapper.toCapacityResponse(null);
        assertNull(nullMappedResponse);
    }

    @Test
    @DisplayName("When_CapacityListConvertedToCapacityResponseList_Expect_SuccessfulConversion")
    void testToCapacityResponseList() {
        List<Capacity> capacities = new ArrayList<>();

        List<Technology> technologies1 = new ArrayList<>();
        technologies1.add(new Technology(1L, "Java", "Programming language"));
        technologies1.add(new Technology(2L, "Python", "Programming language"));
        technologies1.add(new Technology(3L, "JavaScript", "Programming language"));

        List<Technology> technologies2 = new ArrayList<>();
        technologies2.add(new Technology(1L, "Java", "High-level programming language"));
        technologies2.add(new Technology(2L, "Python", "Programming language"));
        technologies2.add(new Technology(3L, "JavaScript", "Programming language"));

        List<Technology> technologies3 = new ArrayList<>();
        technologies3.add(new Technology(1L, "Java", "High-level programming language"));
        technologies3.add(new Technology(2L, "Python", "Programming language"));
        technologies3.add(new Technology(3L, "JavaScript", "Programming language"));


        capacities.add(new Capacity(1L, "Capacity Name 1", "Capacity Description 1", technologies1));
        capacities.add(new Capacity(2L, "Capacity Name 2", "Capacity Description 2", technologies2));
        capacities.add(new Capacity(3L, "Capacity Name 3", "Capacity Description 23", technologies3));


        List<CapacityResponse> mappedResponseList = mapper.toCapacityResponseList(capacities);
        assertNotNull(mappedResponseList);
        assertEquals(capacities.size(), mappedResponseList.size());
        for (int i = 0; i < capacities.size(); i++) {
            Capacity capacity = capacities.get(i);
            CapacityResponse mappedResponse = mappedResponseList.get(i);
            assertEquals(capacity.getId(), mappedResponse.getId());
            assertEquals(capacity.getName(), mappedResponse.getName());
            assertEquals(capacity.getDescription(), mappedResponse.getDescription());
            assertEquals(capacity.getTechnologies().size(), mappedResponse.getTechnologies().size());
        }

        List<CapacityResponse> nullMappedResponseList = mapper.toCapacityResponseList(null);
        assertNull(nullMappedResponseList);
    }
}
