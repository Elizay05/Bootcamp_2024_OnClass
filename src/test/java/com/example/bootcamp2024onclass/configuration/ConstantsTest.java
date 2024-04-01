package com.example.bootcamp2024onclass.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConstantsTest {

    @Test
    @DisplayName("Expect_IllegalStateException_When_AttemptingToInstantiateConstants")
    void testConstantsInstantiation() {
        final Constructor<Constants> constructor;

        try {
            constructor = Constants.class.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            fail("No se encontró el constructor privado de Constants");
            return;
        }

        constructor.setAccessible(true);

        Throwable exception = assertThrows(Exception.class, () -> {
            try {
                constructor.newInstance();
            } catch (InvocationTargetException e) {
                throw e.getTargetException();
            }
        });

        assertTrue(exception instanceof IllegalStateException,
                "Se esperaba que lanzara una IllegalStateException");
    }
}
