package com.example.sjw2g15.absolutebustard;

import android.app.Activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycompany.app.BusStop;
import com.mycompany.app.BusStopUpdate;
import com.mycompany.app.ConfirmMsg;
import com.mycompany.app.LocXY;
import com.mycompany.app.PathMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Communication thread between client and server
 */
public class ClientConThread extends Thread {

    private GoogleMap mMap;
    public volatile Object message;
    public Socket s;
    private MapsActivity caller;
    private LatLng latlng_ext;

    public ClientConThread(MapsActivity caller){
        this.caller = caller;
    }
    public void sendMessage(Object m){ message = m; }
    public void setMap(GoogleMap mMap){ this.mMap = mMap;}

    @Override
    public void run() {
        message = null;

        try {
            s = new Socket("10.9.164.102", 8080);

            while(true) {
                System.out.println("THIS MESSAGE HERE" + message);
                if (message!=null) {
                    communicateServerSend();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }


    }

    public void communicateServerSend() {

        ObjectOutputStream outp = null;
        ObjectInputStream inp = null;

        try {
            outp = new ObjectOutputStream(s.getOutputStream());
            inp = new ObjectInputStream(s.getInputStream());
        } catch (IOException e) {
            //e.printStackTrace();
            // TODO: Handle exception
        }

        System.out.println("THIS MESSAGE HERE");
        if (message != null) {
            if (message.toString().trim() == "QUIT") {
                try {
                    s.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                    // TODO: Handle exception
                }

            } else {
                try {
                    System.out.println("THIS MESSAGE HERE " + message);
                    outp.writeObject(message);
                    final Object serverMsg = inp.readObject();

                    // Determine how to respond to the message
                    if (!(serverMsg instanceof ConfirmMsg)) {
                        System.out.println(serverMsg);

                        latlng_ext = new LatLng(((LocXY)serverMsg).getLat(), ((LocXY)serverMsg).getLon());

                        System.out.println(latlng_ext);
                        System.out.println(mMap);

                        caller.runOnUiThread(new Runnable(){
                            public void run(){
                                mMap.addMarker(new MarkerOptions().position(latlng_ext));
                                //mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng_ext));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng_ext, 12.0f));
                            }
                        });
                    } else if (serverMsg instanceof BusStopUpdate) {
                        caller.runOnUiThread(new Runnable(){
                            public void run(){
                                caller.busStopLabel.setText(((BusStopUpdate)serverMsg).getBusStop());
                            }
                        });
                    } else if (serverMsg instanceof PathMsg) {
                        caller.runOnUiThread(new Runnable(){
                            public void run() {
                                for (BusStop busStop : ((PathMsg) serverMsg).getLocations()) {
                                    LatLng pos = new LatLng(busStop.getLoc().getLat(),
                                            busStop.getLoc().getLat());
                                    MarkerOptions markerOp = new MarkerOptions().position(pos);
                                    markerOp = markerOp.title(busStop.getName());
                                    mMap.addMarker(markerOp);
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    //e.printStackTrace();
                    // TODO: Handle exception
                } catch (ClassNotFoundException e) {
                    //e.printStackTrace();
                    // TODO: Handle exception
                }
            }

        }
    }
}
