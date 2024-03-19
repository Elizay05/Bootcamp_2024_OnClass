package com.example.bootcamp2024onclass.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TechnologyTest {

    @Test
    void technologyConstructor_WithValidArguments_Success() {

        Long id = 1L;
        String name = "Java";
        String description = "Java programming language";


        Technology technology = new Technology(id, name, description);


        assertEquals(id, technology.getId());
        assertEquals(name, technology.getName());
        assertEquals(description, technology.getDescription());
    }

    @Test
    void technologyConstructor_NullName_ExceptionThrown() {

        Long id = 1L;
        String name = null;
        String description = "Java programming language";


        assertThrows(NullPointerException.class, () -> new Technology(id, name, description));
    }

    @Test
    void technologyConstructor_NullDescription_ExceptionThrown() {

        Long id = 1L;
        String name = "Java";
        String description = null;

        
        assertThrows(NullPointerException.class, () -> new Technology(id, name, description));
    }

}
