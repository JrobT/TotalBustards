package com.mycompany.app;

import java.util.ArrayList;

/**
 * Created by User on 11/03/2018.
 */

public class PathMsg {
    private ArrayList<LocXY> locs;

    public PathMsg(ArrayList<LocXY> locs){
        this.locs = locs;
    }

    public ArrayList<LocXY> getLocations(){
        return locs;
    }
}
