package com.example.sjw2g15.absolutebustard;

import android.app.Activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycompany.app.ConfirmMsg;
import com.mycompany.app.Location;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by User on 10/03/2018.
 */

public class ClientConThread extends Thread {

    private GoogleMap mMap;

    public volatile Object message;

    public Socket s;

    private Activity caller;

    private LatLng latlng_ext;

    public ClientConThread(Activity caller){
        this.caller = caller;
    }
    public void sendMessage(Object m){
        message = m;
    }
    public void setMap(GoogleMap mMap){ this.mMap = mMap;}

    @Override
    public void run() {
        message = null;

        try {
            s = new Socket("10.9.164.102", 8080);

            while(true){
                if (message!=null){
                    communicateServerSend();
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }


    }

    public void communicateServerSend(){

        PrintWriter outp = null;
        ObjectInputStream inp = null;
        Object serverMsg = null;

        try {
            outp = new PrintWriter(s.getOutputStream(), true);
            inp = new ObjectInputStream(s.getInputStream());
            //serverMsg = inp.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //convo.append(serverMsg + "\n");

        if (message != null) {
            if (message.toString().trim() == "QUIT") {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //status.setText("Disconnected from server.");

            } else {
                try {

                    //convo.append(message + "\n");
                    outp.println(message);
                    serverMsg = inp.readObject();

                    // Determine how to respond to the message
                    if (!(serverMsg instanceof ConfirmMsg)) {
                        System.out.println(serverMsg);


                        latlng_ext = new LatLng(((Location)serverMsg).lat, ((Location)serverMsg).lon);

                        System.out.println(latlng_ext);
                        System.out.println(mMap);

                        caller.runOnUiThread(new Runnable(){
                            public void run(){
                                mMap.addMarker(new MarkerOptions().position(latlng_ext));
                                //mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng_ext));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng_ext, 12.0f));
                            }
                        });

                    }
                    //convo.append(serverMsg + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
