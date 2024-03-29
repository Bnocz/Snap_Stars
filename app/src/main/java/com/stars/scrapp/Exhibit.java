package com.stars.scrapp;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;
import java.lang.StringBuilder;
public class Exhibit implements Parcelable {

    public boolean exhibitFound;
    public Bitmap displayphoto;
    private String sitename;
    private String status;
    private String descriptionofwork;
    public ExhibitPhoto exhibitPhoto;
    private String url;
    private Integer registryid;
    public ExhibitGeom exhibitGeom;
    private String artists;
    private String siteaddress;
    private String geoLocalArea;
    private String type;
    private String locationonsite;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public Exhibit(HashMap exhibitAttributes) {
        super();
        this.exhibitFound = false;
        this.displayphoto = (Bitmap) exhibitAttributes.get("displayphoto");
        this.sitename = exhibitAttributes.get("sitename").toString();
        this.status = exhibitAttributes.get("status").toString();
        this.descriptionofwork = exhibitAttributes.get("descriptionofwork").toString();
        this.exhibitPhoto = (ExhibitPhoto) exhibitAttributes.get("exhibitPhoto");
        this.url = exhibitAttributes.get("url").toString();
        this.registryid = (int) exhibitAttributes.get("registryid");
        this.exhibitGeom = (ExhibitGeom) exhibitAttributes.get("exhibitGeom");
        this.artists = exhibitAttributes.get("artists").toString();
        this.siteaddress = exhibitAttributes.get("siteaddress").toString();
        this.geoLocalArea = exhibitAttributes.get("geo_local_area").toString();
        this.type = exhibitAttributes.get("type").toString();
        this.locationonsite = exhibitAttributes.get("locationonsite").toString();
    }

    protected Exhibit(Parcel in) {
        sitename = in.readString();
        status = in.readString();
        descriptionofwork = in.readString();
        url = in.readString();
        if (in.readByte() == 0) {
            registryid = null;
        } else {
            registryid = in.readInt();
        }
        artists = in.readString();
        siteaddress = in.readString();
        geoLocalArea = in.readString();
        type = in.readString();
        locationonsite = in.readString();
    }

    public static final Creator<Exhibit> CREATOR = new Creator<Exhibit>() {
        @Override
        public Exhibit createFromParcel(Parcel in) {
            return new Exhibit(in);
        }

        @Override
        public Exhibit[] newArray(int size) {
            return new Exhibit[size];
        }
    };

    public static HashMap getExhibitBaseAttributes() {
        HashMap<String, Object> photoAttributes = new HashMap<String, Object>() {{
            put("sitename", "");
            put("status", "In place");
            put("descriptionofwork", "");
            put("exhibitPhoto", null);
            put("url", "");
            put("registryid", -1);
            put("exhibitGeom", null);
            put("artists", "");
            put("siteaddress", "");
            put("geo_local_area", "");
            put("type", "");
            put("locationonsite", "");
        }};
        return photoAttributes;
    }

    public boolean isExhibitFound() {return this.exhibitFound;}

    public void setExhibitFound(boolean found) {this.exhibitFound = found;}

    public void setBitmap(Bitmap bitmap) {this.exhibitPhoto.displayphoto = bitmap;}

    public String getSitename() {
        return sitename;
    }

    public String getStatus() {
        return status;
    }

    public String getDescriptionofwork() {
        return descriptionofwork;
    }

    public ExhibitPhoto getExhibitPhoto() {
        return exhibitPhoto;
    }

    public void setExhibitPhoto(ExhibitPhoto exhibitPhoto) {
        this.exhibitPhoto = exhibitPhoto;
    }

    public String getUrl() {
        return url;
    }

    public Integer getRegistryid() {
        return registryid;
    }

    public ExhibitGeom getExhibitGeom() {
        return exhibitGeom;
    }

    public String getArtists() {
        return artists;
    }

    public String getSiteaddress() {
        return siteaddress;
    }

    public String getGeoLocalArea() {
        return geoLocalArea;
    }

    public String getType() {
        return type;
    }

    public String getLocationonsite() {
        return locationonsite;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("sitename: " + sitename).append(" status: " + status).append(
                "descriptionofwork: " + descriptionofwork)
                .append("exhibitPhoto: " + exhibitPhoto).append("url: " + url).append("registryid: " + registryid)
                .append("exhibitGeom: " + exhibitGeom).append("artists: " + artists).append("siteaddress: " + siteaddress)
                .append("geoLocalArea: " + geoLocalArea).append("type: " + type).append("locationonsite: " + locationonsite)
                .append("additionalProperties: " + additionalProperties).toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(sitename);
        parcel.writeString(status);
        parcel.writeString(descriptionofwork);
        parcel.writeString(url);
        if (registryid == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(registryid);
        }
        parcel.writeString(artists);
        parcel.writeString(siteaddress);
        parcel.writeString(geoLocalArea);
        parcel.writeString(type);
        parcel.writeString(locationonsite);
    }
}
