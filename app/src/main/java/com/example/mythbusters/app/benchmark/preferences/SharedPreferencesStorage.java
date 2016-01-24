package com.example.mythbusters.app.benchmark.preferences;

import android.content.SharedPreferences;

import com.example.mythbusters.core.benchmark.io.Storage;

import java.io.IOException;

/**
 * Storage backed by {@link android.content.SharedPreferences}
 */
public class SharedPreferencesStorage implements Storage {

    private final SharedPreferences preferences;

    public SharedPreferencesStorage(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void write(String key, String value) throws IOException {
        preferences.edit()
                .putString(key, value)
                .apply();
    }

    @Override
    public String read(String key) throws IOException {
        return preferences.getString(key, null);
    }

}
