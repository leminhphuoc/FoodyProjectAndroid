package hcmute.edu.vn.nhom02.foodyproject.model;

import java.util.Date;

public class Restaurant {
    private int Id;
    private String Name;
    private int ProvinceId;
    private String Thumbnail;
    private int TagId;
    private String Description;
    private int LocationId;
    private int TimeOpen;
    private int TimeClose;
    private String wifiName;
    private String wifiPassword;


    public Restaurant() {
    }

    public Restaurant(int id, String name, int provinceId, String thumbnail, int tagId, String description, int locationId, int timeOpen, int timeClose) {
        Id = id;
        Name = name;
        ProvinceId = provinceId;
        Thumbnail = thumbnail;
        TagId = tagId;
        Description = description;
        LocationId = locationId;
        TimeOpen = timeOpen;
        TimeClose = timeClose;
    }



    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(int provinceId) {
        ProvinceId = provinceId;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public int getTagId() {
        return TagId;
    }

    public void setTagId(int tagId) {
        TagId = tagId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public int getTimeOpen() {
        return TimeOpen;
    }

    public void setTimeOpen(int timeOpen) {
        TimeOpen = timeOpen;
    }

    public int getTimeClose() {
        return TimeClose;
    }

    public void setTimeClose(int timeClose) {
        TimeClose = timeClose;
    }
}
