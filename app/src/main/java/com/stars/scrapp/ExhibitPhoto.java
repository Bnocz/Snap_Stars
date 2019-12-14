package com.stars.scrapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

import java.lang.StringBuilder;

public class ExhibitPhoto implements Parcelable {

    public Bitmap displayphoto;
    private String mimetype;
    private String format;
    private String filename;
    private Integer width;
    private String id;
    private Integer height;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public ExhibitPhoto(HashMap photoAttributes) {
        super();
        this.displayphoto = (Bitmap) photoAttributes.get("displayphoto");
        this.mimetype = photoAttributes.get("id").toString();
        this.format = photoAttributes.get("format").toString();
        this.filename = photoAttributes.get("filename").toString();
        this.width = (int) photoAttributes.get("width");
        this.id = photoAttributes.get("id").toString();
        this.height = (int) photoAttributes.get("height");
    }

    protected ExhibitPhoto(Parcel in) {
        displayphoto = in.readParcelable(Bitmap.class.getClassLoader());
        mimetype = in.readString();
        format = in.readString();
        filename = in.readString();
        if (in.readByte() == 0) {
            width = null;
        } else {
            width = in.readInt();
        }
        id = in.readString();
        if (in.readByte() == 0) {
            height = null;
        } else {
            height = in.readInt();
        }
    }

    public static final Creator<ExhibitPhoto> CREATOR = new Creator<ExhibitPhoto>() {
        @Override
        public ExhibitPhoto createFromParcel(Parcel in) {
            return new ExhibitPhoto(in);
        }

        @Override
        public ExhibitPhoto[] newArray(int size) {
            return new ExhibitPhoto[size];
        }
    };

    public static HashMap getExhibitPhotoBaseAttributes() {
        HashMap<String, Object> photoAttributes = new HashMap<String, Object>() {{
            put("mimetype", "");
            put("format", "");
            put("filename", "");
            put("width", 0);
            put("id", "");
            put("height", 0);
        }};
        return photoAttributes;
    }

    public Bitmap getDisplayphoto () {return displayphoto; }

    public void setDisplayphoto (Bitmap displayphoto){this.displayphoto = displayphoto;}

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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("mimetype: " + mimetype).append(" format: " + format).append(" filename: " + filename)
                .append(" width: " + width).append(" id: " + id).append(" height: " + height)
                .append(" additionalProperties: " + additionalProperties).toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(displayphoto, i);
        parcel.writeString(mimetype);
        parcel.writeString(format);
        parcel.writeString(filename);
        if (width == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(width);
        }
        parcel.writeString(id);
        if (height == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(height);
        }
    }
}