package com.example.bootcamp2024onclass.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("When_TechnologyConstructorWithId_Expect_Success")
    void technologyConstructor_WithId_Success() {
        Long id = 1L;
        Technology technology = new Technology(id);
        assertNotNull(technology);
        assertEquals(id, technology.getId());
        assertNull(technology.getName());
        assertNull(technology.getDescription());
    }

    @Test
    @DisplayName("When_SetName_Expect_Success")
    void setName_ValidName_Success() {
        Technology technology = new Technology();
        String name = "Java";
        technology.setName(name);
        assertEquals(name, technology.getName());
    }

    @Test
    @DisplayName("When_SetDescription_Expect_Success")
    void setDescription_ValidDescription_Success() {
        Technology technology = new Technology();
        String description = "Java programming language";
        technology.setDescription(description);
        assertEquals(description, technology.getDescription());
    }

    @Test
    @DisplayName("When_SetId_Expect_Success")
    void setId_ValidId_Success() {
        Technology technology = new Technology();
        Long id = 1L;
        technology.setId(id);
        assertEquals(id, technology.getId());
    }

}
