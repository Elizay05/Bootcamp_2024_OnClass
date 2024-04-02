package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.request.AddBootcampRequest;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IBootcampRequestMapperImplTest {
    private final IBootcampRequestMapperImpl mapper = new IBootcampRequestMapperImpl();

    @Test
    @DisplayName("When_AddBootcampRequestMappedToBootcamp_Expect_SuccessfulMapping")
    void testAddRequestToBootcamp() {

        List<Long> capacityIds = Arrays.asList(1L, 2L, 3L);
        String name = "Bootcamp Name";
        String description = "Bootcamp Description";
        AddBootcampRequest validRequest = new AddBootcampRequest(name, description, capacityIds);

        Bootcamp mappedBootcamp = mapper.addRequestToBootcamp(validRequest);
        assertNotNull(mappedBootcamp);
        assertNull(mappedBootcamp.getId());
        assertEquals(name, mappedBootcamp.getName());
        assertEquals(description, mappedBootcamp.getDescription());
        assertEquals(capacityIds.size(), mappedBootcamp.getCapacities().size());

        Bootcamp nullMappedBootcamp = mapper.addRequestToBootcamp(null);
        assertNull(nullMappedBootcamp);
    }
}
