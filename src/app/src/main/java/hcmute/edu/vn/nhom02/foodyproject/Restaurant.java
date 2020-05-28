package hcmute.edu.vn.nhom02.foodyproject;

import java.util.Date;

public class Restaurant {
    private String Name;
    private String Address;
    private String Type;
    private String RangePrice;
    private String TimeOpen;
    private String TimeClose;
    private String Menu;
    private int Thumbnail;

    public Restaurant() {
    }

    public Restaurant(String name, String address, String type, int thumbnail) {
        Name = name;
        Address = address;
        Type = type;
        Thumbnail = thumbnail;
    }

    public Restaurant(String name, String address, String type, String rangePrice, String timeOpen, String timeClose, String menu, int thumbnail) {
        Name = name;
        Address = address;
        Type = type;
        RangePrice = rangePrice;
        TimeOpen = timeOpen;
        TimeClose = timeClose;
        Menu = menu;
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

    public String getRangePrice() {
        return RangePrice;
    }

    public void setRangePrice(String rangePrice) {
        RangePrice = rangePrice;
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

    public String getMenu() {
        return Menu;
    }

    public void setMenu(String menu) {
        Menu = menu;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}
