package com.example.sjw2g15.absolutebustard;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by User on 10/03/2018.
 */

public class ClientConThread extends Thread {

    private GoogleMap mMap;

    public volatile Object message;

    public Socket s;

    public ClientConThread(GoogleMap mMap){
        this.mMap = mMap;
    }

    public void sendMessage(Object m){
        message = m;
    }

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
        BufferedReader inp = null;
        Object serverMsg = null;

        try {
            outp = new PrintWriter(s.getOutputStream(), true);
            inp = new BufferedReader(new InputStreamReader(s.getInputStream()));
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
                    serverMsg = inp.readLine();

                    LatLng pos = new LatLng(((Location)serverMsg).lat, ((Location)serverMsg).lon);
                    mMap.addMarker(new MarkerOptions().position(pos).title(message.toString()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));


                    System.out.println(serverMsg);
                    //convo.append(serverMsg + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
