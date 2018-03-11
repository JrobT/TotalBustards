package com.mycompany.app;

import java.io.Serializable;

/**
 * Created by User on 11/03/2018.
 */

public class PathRequestMsg implements Serializable {
    private String startingPoint;
    private String endPoint;

    public PathRequestMsg(String startingPoint, String endPoint){
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
    }

    public String getStartingPoint(){
        return startingPoint;
    }

    public String getEndPoint(){
        return endPoint;
    }
}
