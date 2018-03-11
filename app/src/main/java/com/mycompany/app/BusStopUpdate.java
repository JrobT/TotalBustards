package com.mycompany.app;

import java.io.Serializable;

/**
 * Created by User on 11/03/2018.
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
