package com.stars.scrapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.StringBuilder;


public class ExhibitGeom implements Parcelable {

    public String type;
    public List<Double> coordinates = null;
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @param type
     * @param coordinates
     */
    public ExhibitGeom(String type, List<Double> coordinates) {
        super();
        this.type = type;
        this.coordinates = coordinates;
    }

    protected ExhibitGeom(Parcel in) {
        type = in.readString();
    }

    public static final Creator<ExhibitGeom> CREATOR = new Creator<ExhibitGeom>() {
        @Override
        public ExhibitGeom createFromParcel(Parcel in) {
            return new ExhibitGeom(in);
        }

        @Override
        public ExhibitGeom[] newArray(int size) {
            return new ExhibitGeom[size];
        }
    };

    public String getType() {
        return type;
    }

    public double getLatitude(){
        return coordinates.get(0);
    }

    public double getLongtitude(){
        return coordinates.get(1);
    }


    public List<Double> getCoordinates() {
        return coordinates;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("type: " + type).append(" coordinates: " + coordinates).append(
                " additionalProperties: " + additionalProperties).toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
    }
}