package com.example.bootcamp2024onclass.adapters.driving.http.mapper;

import com.example.bootcamp2024onclass.adapters.driving.http.dto.reponse.BootcampResponse;
import com.example.bootcamp2024onclass.domain.model.Bootcamp;
import com.example.bootcamp2024onclass.domain.model.Capacity;
import com.example.bootcamp2024onclass.domain.model.Technology;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IBootcampResponseMapperImplTest {

    private final IBootcampResponseMapperImpl mapper = new IBootcampResponseMapperImpl();
    @Test
    @DisplayName("When_BootcampListConvertedToBootcampResponseList_Expect_SuccessfulConversion")
    void testToBootcampResponseList() {
        List<Bootcamp> bootcamps = new ArrayList<>();

        List<Technology> technologies = new ArrayList<>();
        technologies.add(new Technology(1L, "Technology 1", "Description 1"));
        technologies.add(new Technology(2L, "Technology 2", "Description 2"));
        technologies.add(new Technology(3L, "Technology 3", "Description 3"));

        List<Capacity> capacities1 = new ArrayList<>();
        capacities1.add(new Capacity(1L, "Java", "Programming language", technologies));
        capacities1.add(new Capacity(2L, "Python", "Programming language", technologies));
        capacities1.add(new Capacity(3L, "JavaScript", "Programming language", technologies));

        List<Capacity> capacities2 = new ArrayList<>();
        capacities2.add(new Capacity(1L, "Java", "High-level programming language", technologies));
        capacities2.add(new Capacity(2L, "Python", "Programming language", technologies));
        capacities2.add(new Capacity(3L, "JavaScript", "Programming language", technologies));

        List<Capacity> capacities3 = new ArrayList<>();
        capacities3.add(new Capacity(1L, "Java", "High-level programming language", technologies));
        capacities3.add(new Capacity(2L, "Python", "Programming language", technologies));
        capacities3.add(new Capacity(3L, "JavaScript", "Programming language", technologies));


        bootcamps.add(new Bootcamp(1L, "Capacity Name 1", "Capacity Description 1", capacities1));
        bootcamps.add(new Bootcamp(2L, "Capacity Name 2", "Capacity Description 2", capacities2));
        bootcamps.add(new Bootcamp(3L, "Capacity Name 3", "Capacity Description 23", capacities3));


        List<BootcampResponse> mappedResponseList = mapper.toBootcampResponseList(bootcamps);
        assertNotNull(mappedResponseList);
        assertEquals(bootcamps.size(), mappedResponseList.size());
        for (int i = 0; i < bootcamps.size(); i++) {
            Bootcamp bootcamp = bootcamps.get(i);
            BootcampResponse mappedResponse = mappedResponseList.get(i);
            assertEquals(bootcamp.getId(), mappedResponse.getId());
            assertEquals(bootcamp.getName(), mappedResponse.getName());
            assertEquals(bootcamp.getDescription(), mappedResponse.getDescription());
            assertEquals(bootcamp.getCapacities().size(), mappedResponse.getCapacities().size());
        }

        List<BootcampResponse> nullMappedResponseList = mapper.toBootcampResponseList(null);
        assertNull(nullMappedResponseList);
    }
}
