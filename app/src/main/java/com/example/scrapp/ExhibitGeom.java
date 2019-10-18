package com.example.scrapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.StringBuilder;


public class ExhibitGeom {

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

}