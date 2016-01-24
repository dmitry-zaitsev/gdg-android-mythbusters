package com.example.mythbusters.app.benchmark.serialization;

import com.example.mythbusters.core.benchmark.serialization.Serializer;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * Serializes objects using Java standard serialization
 */
public class JavaSerializer implements Serializer {

    @Override
    public byte[] serialize(Object object) {
        Serializable serializable = (Serializable) object;

        return SerializationUtils.serialize(serializable);
    }

}
