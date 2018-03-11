package com.mycompany.app;

import java.io.Serializable;

/**
 * Created by User on 10/03/2018.
 */

public class Location implements Serializable {
    public double lat;
    public double lon;

    public Location(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }
}
