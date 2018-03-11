package com.mycompany.app;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by User on 11/03/2018.
 */

public class UpdateUserLocMsg implements Serializable {
    private LatLng loc;
    private String user;

    public UpdateUserLocMsg(LatLng loc, String id){
        this.loc = loc;
        this.user = id;
    }

    public LatLng getLoc(){
        return loc;
    }

    public String getUser(){
        return user;
    }
}
