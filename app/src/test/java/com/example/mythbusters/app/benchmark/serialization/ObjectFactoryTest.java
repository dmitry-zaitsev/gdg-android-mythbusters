package com.example.mythbusters.app.benchmark.serialization;

import com.example.mythbusters.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.Serializable;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ObjectFactoryTest {

    JavaSerializer javaSerializer;
    AndroidParcelableSerializer parcelableSerializer;
    ObjectFactory objectFactory;

    @Before
    public void setUp() throws Exception {
        javaSerializer = new JavaSerializer();
        parcelableSerializer = new AndroidParcelableSerializer();

        objectFactory = new ObjectFactory();
    }

    @Test
    public void createSmallSerializeableObject() throws Exception {
        // When
        Object object = objectFactory.createSmallObject();

        // Then
        assertTrue(object instanceof Serializable);
        assertTrue(javaSerializer.serialize(object).length > 0);
    }

    @Test
    public void createSmallParcelableObject() throws Exception {
        // When
        Object object = objectFactory.createSmallObject();

        // Then
        assertTrue(object instanceof Serializable);
        assertTrue(parcelableSerializer.serialize(object).length > 0);
    }

    @Test
    public void createBigSerializeableObject() throws Exception {
        // When
        Object object = objectFactory.createBigObject();

        // Then
        assertTrue(object instanceof Serializable);
        assertTrue(javaSerializer.serialize(object).length > 0);
    }

    @Test
    public void createBigParcelableObject() throws Exception {
        // When
        Object object = objectFactory.createBigObject();

        // Then
        assertTrue(object instanceof Serializable);
        assertTrue(parcelableSerializer.serialize(object).length > 0);
    }

}