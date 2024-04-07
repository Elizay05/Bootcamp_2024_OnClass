package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddCapacityRequest;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ICapacityRequestMapperImplTest {

    private final ICapacityRequestMapperImpl mapper = new ICapacityRequestMapperImpl();

    @Test
    @DisplayName("When_AddCapacityRequestMappedToCapacity_Expect_SuccessfulMapping")
    void testAddRequestToCapacity() {
        List<Long> technologyIds = Arrays.asList(1L, 2L, 3L);
        String name = "Capacity Name";
        String description = "Capacity Description";
        AddCapacityRequest validRequest = new AddCapacityRequest(name, description, technologyIds);

        Capacity mappedCapacity = mapper.addRequestToCapacity(validRequest);
        assertNotNull(mappedCapacity);
        assertNull(mappedCapacity.getId());
        assertEquals(name, mappedCapacity.getName());
        assertEquals(description, mappedCapacity.getDescription());
        assertEquals(technologyIds.size(), mappedCapacity.getTechnologies().size());

        Capacity nullMappedCapacity = mapper.addRequestToCapacity(null);
        assertNull(nullMappedCapacity);
    }
}
