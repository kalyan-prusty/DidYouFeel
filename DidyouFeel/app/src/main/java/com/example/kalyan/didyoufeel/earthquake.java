package com.example.kalyan.didyoufeel;

/**
 * Created by KALYAN on 31-05-2017.
 */

public class earthquake {
    private String place,magnitude,url;
    private long date;
        public earthquake(String place,long date,String mag,String url){
        this.place = place;
        this.date = date;
        this.magnitude = mag;
            this.url = url;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public long getDateinmillis() {
        return date;
    }

    public String getPlace() {
        return place;
    }
    public String getUri(){
        return url;
    }
}
