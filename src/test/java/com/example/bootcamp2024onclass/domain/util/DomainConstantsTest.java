package com.example.bootcamp2024onclass.domain.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class DomainConstantsTest {

    @Test
    void testConstructorInvocation() throws NoSuchMethodException {
        Constructor<DomainConstants> constructor = DomainConstants.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }
}
