package com.example.sjw2g15.absolutebustard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mycompany.app.LocXY;
import com.mycompany.app.PathRequestMsg;
import com.mycompany.app.UpdateUserLocMsg;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private TextView busStopLabel;

    private GoogleMap mMap;
    private String startStop, finalStop;
    private double[] coordStart, coordStop;

    private ClientConThread communicator;

    private String myId;
    private Marker userMarker;

    private double[] debugCoords;
    private double debugMovSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent getI = getIntent();
        startStop = getI.getStringExtra("start");
        finalStop = getI.getStringExtra("stop");
        myId = getI.getStringExtra("myId");

        coordStart = null;
        coordStop = null;

        busStopLabel = findViewById(R.id.closest_to);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        communicator = new ClientConThread(this);
        communicator.start();

        // Debugging
        debugCoords = new double[]{50.937, -1.399};
        debugMovSpeed = 0.00025;

        View[] mov = new View[]{ findViewById(R.id.mov_left),
                findViewById(R.id.mov_up),
                findViewById(R.id.mov_down),
                findViewById(R.id.mov_right)};

        mov[0].setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debugCoords[1] -= debugMovSpeed;
                updateUserPos(new LatLng(debugCoords[0], debugCoords[1]));
            }
        });
        mov[1].setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debugCoords[0] += debugMovSpeed;
                updateUserPos(new LatLng(debugCoords[0], debugCoords[1]));
            }
        });
        mov[2].setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debugCoords[0] -= debugMovSpeed;
                updateUserPos(new LatLng(debugCoords[0], debugCoords[1]));
            }
        });
        mov[3].setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debugCoords[1] += debugMovSpeed;
                updateUserPos(new LatLng(debugCoords[0], debugCoords[1]));
            }
        });

    }

    private void updateUserPos(LatLng loc){
        Toast.makeText(
                getBaseContext(),
                "Location changed: Lat: " + loc.latitude + " Lng: "
                        + loc.longitude, Toast.LENGTH_SHORT).show();

        UpdateUserLocMsg m = new UpdateUserLocMsg(new LocXY(loc.latitude, loc.longitude), myId);
        communicator.sendMessage(m);
//        caller.runOnUiThread(new Runnable(){
//            public void run(){
        userMarker.setPosition(new LatLng(debugCoords[0], debugCoords[1]));
//            }
//        });
    }

    /**
     * Listener class to get coordinates
     */
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            updateUserPos(new LatLng(loc.getLatitude(), loc.getLongitude()));
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        userMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(debugCoords[0], debugCoords[1]))
                .title("Me").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        communicator.setMap(mMap);
        communicator.sendMessage(new PathRequestMsg(startStop, finalStop));

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling request permissions
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
    }
}
