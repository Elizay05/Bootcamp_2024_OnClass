package com.example.bootcamp2024onclass.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TechnologyTest {

    @Test
    @DisplayName("When_TechnologyConstructorWithValidArguments_Expect_Success")
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
    @DisplayName("Expect_Exception_When_NullNamePassedToTechnologyConstructor")
    void technologyConstructor_NullName_ExceptionThrown() {

        Long id = 1L;
        String name = null;
        String description = "Java programming language";


        assertThrows(NullPointerException.class, () -> new Technology(id, name, description));
    }

    @Test
    @DisplayName("Expect_Exception_When_NullDescriptionPassedToTechnologyConstructor")
    void technologyConstructor_NullDescription_ExceptionThrown() {

        Long id = 1L;
        String name = "Java";
        String description = null;

        
        assertThrows(NullPointerException.class, () -> new Technology(id, name, description));
    }

}
