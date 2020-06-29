package hcmute.edu.vn.nhom02.foodyproject.model;

import java.util.Date;

public class Restaurant {
    private String Name;
    private String Address;
    private String Type;
    private String TimeOpen;
    private String TimeClose;
    private String Thumbnail;

    public Restaurant() {
    }

    public Restaurant(String name, String address, String type, String thumbnail) {
        Name = name;
        Address = address;
        Type = type;
        Thumbnail = thumbnail;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTimeOpen() {
        return TimeOpen;
    }

    public void setTimeOpen(String timeOpen) {
        TimeOpen = timeOpen;
    }

    public String getTimeClose() {
        return TimeClose;
    }

    public void setTimeClose(String timeClose) {
        TimeClose = timeClose;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }
}
