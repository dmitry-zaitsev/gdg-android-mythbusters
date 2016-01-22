package com.example.mythbusters.core.benchmark.io;

import java.io.IOException;

/**
 * Storage which supports write and read operations for {@link String} values.
 */
public interface Storage {

    /**
     * Write value into storage under given key.
     *
     * @param key   key under which value can be later accessed
     * @param value value to store
     * @throws IOException if write failed
     */
    void write(String key, String value) throws IOException;

    /**
     * @param key key under which value was stored
     * @return value stored under given key
     * @throws IOException if read failed or key was not found
     */
    String read(String key) throws IOException;

}
