package com.example.scrapp;

import java.util.HashMap;
import java.util.Map;

import java.lang.StringBuilder;

public class ExhibitPhoto {

    private String mimetype;
    private String format;
    private String filename;
    private Integer width;
    private String id;
    private Integer height;
    private Boolean thumbnail;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public ExhibitPhoto() {
    }

    /**
     * @param id
     * @param mimetype
     * @param thumbnail
     * @param height
     * @param width
     * @param filename
     * @param format
     */
    public ExhibitPhoto(String mimetype, String format, String filename, Integer width, String id, Integer height, Boolean thumbnail) {
        super();
        this.mimetype = mimetype;
        this.format = format;
        this.filename = filename;
        this.width = width;
        this.id = id;
        this.height = height;
        this.thumbnail = thumbnail;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Boolean thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("mimetype: " + mimetype).append(" format: " + format).append(" filename: " + filename)
                .append(" width: " + width).append(" id: " + id).append(" height: " + height).append(" thumbnail: " + thumbnail)
                .append(" additionalProperties: " + additionalProperties).toString();
    }

}