package com.example.mythbusters.app.benchmark.serialization;

import android.graphics.Rect;
import android.os.Parcel;

import com.example.mythbusters.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class AndroidParcelableSerializerTest {

    @Test
    public void serialize() throws Exception {
        // Given
        AndroidParcelableSerializer serializer = new AndroidParcelableSerializer();

        Rect expected = new Rect(1, 2, 3, 4);

        // When
        byte[] bytes = serializer.serialize(expected);
        Rect result = unmarshall(bytes);

        // Then
        assertEquals(expected, result);
    }

    private Rect unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        Rect result = Rect.CREATOR.createFromParcel(parcel);
        parcel.recycle();

        return result;
    }

}