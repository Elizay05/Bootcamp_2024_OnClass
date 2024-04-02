package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampResponse;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IBootcampResponseMapperImplTest {

    private final IBootcampResponseMapperImpl mapper = new IBootcampResponseMapperImpl();

    @Test
    @DisplayName("When_BootcampConvertedToBootcampResponse_Expect_SuccessfulConversion")
    void testToBootcampResponse() {

        List<Technology> technologies = Arrays.asList(
                new Technology(1L),
                new Technology(2L),
                new Technology(3L)
        );

        List<Capacity> capacities = new ArrayList<>();
        capacities.add(new Capacity(1L, "Java", "Programming language", technologies));
        capacities.add(new Capacity(2L, "Python", "High-level programming language", technologies));
        capacities.add(new Capacity(3L, "JavaScript", "High-level programming language", technologies));
        Bootcamp bootcamp = new Bootcamp(1L, "Bootcamp Name", "Bootcamp Description", capacities);

        BootcampResponse mappedResponse = mapper.toBootcampResponse(bootcamp);
        assertNotNull(mappedResponse);
        assertEquals(bootcamp.getId(), mappedResponse.getId());
        assertEquals(bootcamp.getName(), mappedResponse.getName());
        assertEquals(bootcamp.getDescription(), mappedResponse.getDescription());
        assertEquals(technologies.size(), mappedResponse.getCapacities().size());

        BootcampResponse nullMappedResponse = mapper.toBootcampResponse(null);
        assertNull(nullMappedResponse);
    }
}
