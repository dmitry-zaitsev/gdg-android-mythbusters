package com.example.mythbusters.app.benchmark.serialization;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JavaSerializerTest {

    @Test
    public void serialize() throws Exception {
        // Given
        JavaSerializer serializer = new JavaSerializer();

        String expected = "expected";

        // When
        byte[] bytes = serializer.serialize(expected);
        final Object result = SerializationUtils.deserialize(bytes);

        // Then
        assertEquals(expected, result);
    }

}