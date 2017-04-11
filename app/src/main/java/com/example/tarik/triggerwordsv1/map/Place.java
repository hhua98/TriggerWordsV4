package com.example.tarik.triggerwordsv1.map;

/**
 * Created by huanghe on 10/04/2017.
 */

import android.graphics.Bitmap;

import java.net.URL;
import java.util.List;

/**
 * Created by Chad on 7/4/17.
 */

public class Place {

    private String placeId;
    private String name;
    private String vicinity;
    double latitude;
    double longitude;
    private String phone;
    private URL web;
    private double rate;
    private List<Bitmap> photos;

    public Place(String placeId, String name, String vicinity, double latitude, double longitude, String phone, URL web, double rate, List<Bitmap> photos) {
        this.placeId = placeId;
        this.name = name;
        this.vicinity = vicinity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.web = web;
        this.rate = rate;
        this.photos = photos;
    }


    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public URL getWeb() {
        return web;
    }

    public void setWeb(URL web) {
        this.web = web;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public List<Bitmap> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Bitmap> photos) {
        this.photos = photos;
    }
}
