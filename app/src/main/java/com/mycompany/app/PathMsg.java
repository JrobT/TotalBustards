package com.mycompany.app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Path message object
 */
public class PathMsg implements Serializable {
    private ArrayList<BusStop> locs;

    public PathMsg(ArrayList<BusStop> locs){
        this.locs = locs;
    }

    public ArrayList<BusStop> getLocations(){
        return locs;
    }
}
