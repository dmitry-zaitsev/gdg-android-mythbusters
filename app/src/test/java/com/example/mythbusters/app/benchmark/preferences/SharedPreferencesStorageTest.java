package com.example.mythbusters.app.benchmark.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mythbusters.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SharedPreferencesStorageTest {

    SharedPreferencesStorage storage;
    SharedPreferences preferences;

    @Before
    public void setUp() throws Exception {
        preferences = ShadowApplication.getInstance()
                .getSharedPreferences("preferences", Context.MODE_PRIVATE);

        storage = new SharedPreferencesStorage(preferences);
    }

    @Test
    public void write() throws Exception {
        // When
        storage.write("key", "value");

        // Then
        assertEquals(
                "value",
                preferences.getString("key", null)
        );
    }

    @Test
    public void read() throws Exception {
        // Given
        String expected = "value";

        preferences.edit()
                .putString("key", expected)
                .apply();

        // When
        String result = storage.read("key");

        // Then
        assertEquals(expected, result);
    }
}