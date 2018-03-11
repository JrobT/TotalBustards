package com.mycompany.app;

import java.io.Serializable;

/**
 * String for TextView displaying Bus Stop name
 */
public class BusStopUpdate implements Serializable {
    private String busStop;

    public BusStopUpdate(String busStop){
        this.busStop = busStop;
    }

    public String getBusStop(){
        return busStop;
    }
}
