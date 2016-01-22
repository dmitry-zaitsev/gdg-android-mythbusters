package com.example.mythbusters.core.benchmark.serialization;

/**
 * Serializes the given object
 */
public interface Serializer {

    /**
     * @param object object to serialize
     * @return serialized object in bytes
     */
    byte[] serialize(Object object);

}
