package com.mycompany.app;

import java.io.Serializable;

/**
 * LatLon coordinates object
 */
public class LocXY implements Serializable {
    private double lat;
    private double lon;

    public LocXY(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() { return lat; }
    public double getLon() { return lon; }
}
