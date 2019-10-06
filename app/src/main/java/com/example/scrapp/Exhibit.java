package com.example.scrapp;

import java.util.HashMap;
import java.util.Map;
import java.lang.StringBuilder;
public class Exhibit {

    private String status;
    private String descriptionofwork;
    private String yearofinstallation;
    private ExhibitPhoto exhibitPhoto;
    private String url;
    private Integer registryid;
    private ExhibitGeom exhibitGeom;
    private String neighbourhood;
    private String ownership;
    private String artists;
    private String siteaddress;
    private String artistprojectstatement;
    private String geoLocalArea;
    private String type;
    private String primarymaterial;
    private String locationonsite;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Exhibit() {
    }

    /**
     *
     * @param descriptionofwork
     * @param status
     * @param yearofinstallation
     * @param exhibitPhoto
     * @param registryid
     * @param exhibitGeom
     * @param primarymaterial
     * @param type
     * @param geoLocalArea
     * @param siteaddress
     * @param url
     * @param artists
     * @param ownership
     * @param locationonsite
     * @param artistprojectstatement
     * @param neighbourhood
     */
    public Exhibit(String status, String descriptionofwork, String yearofinstallation, ExhibitPhoto exhibitPhoto, String url, Integer registryid, ExhibitGeom exhibitGeom, String neighbourhood, String ownership, String artists, String siteaddress, String artistprojectstatement, String geoLocalArea, String type, String primarymaterial, String locationonsite) {
        super();
        this.status = status;
        this.descriptionofwork = descriptionofwork;
        this.yearofinstallation = yearofinstallation;
        this.exhibitPhoto = exhibitPhoto;
        this.url = url;
        this.registryid = registryid;
        this.exhibitGeom = exhibitGeom;
        this.artists = artists;
        this.siteaddress = siteaddress;
        this.geoLocalArea = geoLocalArea;
        this.type = type;
        this.locationonsite = locationonsite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescriptionofwork() {
        return descriptionofwork;
    }

    public void setDescriptionofwork(String descriptionofwork) {
        this.descriptionofwork = descriptionofwork;
    }

    public String getYearofinstallation() {
        return yearofinstallation;
    }

    public void setYearofinstallation(String yearofinstallation) {
        this.yearofinstallation = yearofinstallation;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRegistryid() {
        return registryid;
    }

    public void setRegistryid(Integer registryid) {
        this.registryid = registryid;
    }

    public ExhibitGeom getExhibitGeom() {
        return exhibitGeom;
    }

    public void setExhibitGeom(ExhibitGeom exhibitGeom) {
        this.exhibitGeom = exhibitGeom;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getSiteaddress() {
        return siteaddress;
    }

    public void setSiteaddress(String siteaddress) {
        this.siteaddress = siteaddress;
    }

    public String getGeoLocalArea() {
        return geoLocalArea;
    }

    public void setGeoLocalArea(String geoLocalArea) {
        this.geoLocalArea = geoLocalArea;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocationonsite() {
        return locationonsite;
    }

    public void setLocationonsite(String locationonsite) {
        this.locationonsite = locationonsite;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("status: " + status).append("descriptionofwork: " + descriptionofwork).append("yearofinstallation: " + yearofinstallation).append("exhibitPhoto: " + exhibitPhoto).append("url: " + url).append("registryid: " + registryid).append("exhibitGeom: " + exhibitGeom).append("neighbourhood: " + neighbourhood).append("ownership: " + ownership).append("artists: " + artists).append("siteaddress: " + siteaddress).append("artistprojectstatement: " + artistprojectstatement).append("geoLocalArea: " + geoLocalArea).append("type: " + type).append("primarymaterial: " + primarymaterial).append("locationonsite: " + locationonsite).append("additionalProperties: " + additionalProperties).toString();
    }

}
