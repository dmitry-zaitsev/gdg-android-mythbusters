package com.example.mythbusters.app.benchmark.serialization;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mythbusters.core.benchmark.serialization.MeasureSerializationUseCase;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Creates objects which are both {@link java.io.Serializable} and {@link android.os.Parcelable}
 */
public class ObjectFactory implements MeasureSerializationUseCase.ObjectFactory {

    @Override
    public final Object createSmallObject() {
        return new SmallObject(
                10,
                "small object",
                "http://example.com"
        );
    }

    @Override
    public Object createBigObject() {
        SmallObject[] smallObjects = new SmallObject[20];
        Arrays.fill(smallObjects, createSmallObject());

        return new BigObject(
                20,
                "big object",
                asList(smallObjects)
        );
    }

    private static class SmallObject implements Serializable, Parcelable {

        public static final Creator<SmallObject> CREATOR = new Creator<SmallObject>() {
            public SmallObject createFromParcel(Parcel source) {
                return new SmallObject(source);
            }

            public SmallObject[] newArray(int size) {
                return new SmallObject[size];
            }
        };
        public final long id;
        public final String name;
        public final String url;

        public SmallObject(long id, String name, String url) {
            this.id = id;
            this.name = name;
            this.url = url;
        }

        protected SmallObject(Parcel in) {
            this.id = in.readLong();
            this.name = in.readString();
            this.url = in.readString();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.id);
            dest.writeString(this.name);
            dest.writeString(this.url);
        }

    }

    private static class BigObject implements Serializable, Parcelable {

        public static final Creator<BigObject> CREATOR = new Creator<BigObject>() {
            public BigObject createFromParcel(Parcel source) {
                return new BigObject(source);
            }

            public BigObject[] newArray(int size) {
                return new BigObject[size];
            }
        };
        public final long id;
        public final String name;
        public final List<SmallObject> objects;

        public BigObject(long id, String name, List<SmallObject> objects) {
            this.id = id;
            this.name = name;
            this.objects = objects;
        }

        protected BigObject(Parcel in) {
            this.id = in.readLong();
            this.name = in.readString();
            this.objects = in.createTypedArrayList(SmallObject.CREATOR);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.id);
            dest.writeString(this.name);
            dest.writeTypedList(objects);
        }
    }

}
