package com.example.mythbusters.app.benchmark.serialization;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mythbusters.core.benchmark.serialization.Serializer;

/**
 * Serializes objects using Android {@link android.os.Parcelable}
 */
public class AndroidParcelableSerializer implements Serializer {

    @Override
    public byte[] serialize(Object object) {
        Parcelable parcelable = (Parcelable) object;

        return marshall(parcelable);
    }

    private byte[] marshall(Parcelable parceable) {
        Parcel parcel = Parcel.obtain();
        parceable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

}
