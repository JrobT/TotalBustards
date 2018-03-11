package com.mycompany.app;

import java.io.Serializable;

/**
 * Created by User on 10/03/2018.
 */

public class LocXY implements Serializable {
    public double lat;
    public double lon;

    public LocXY(double lat, double lon){
        this.lat = lat;
        this.lon = lon;
    }
}
