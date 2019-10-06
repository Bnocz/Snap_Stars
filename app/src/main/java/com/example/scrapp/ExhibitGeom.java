package com.example.scrapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.StringBuilder;


public class ExhibitGeom {

    private String type;
    private List<Double> coordinates = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public ExhibitGeom() {
    }

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

    public void setType(String type) {
        this.type = type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
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