package com.example.sjw2g15.absolutebustard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner start, stop;
    private View list;
    private View go;
    private CheckBox location;

    public String initUserID() {

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        // get IMEI Permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 0);
//            initUserID();
//            return;
        }

        return tm.getDeviceId();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        location = findViewById(R.id.useLocation);
        list = findViewById(R.id.listView);
        go = findViewById(R.id.go);

        start.setOnItemSelectedListener( new BusStopListener(start, stop, list, go) );
        stop.setOnItemSelectedListener( new BusStopListener(start, stop, list, go)  );

        final String myId = initUserID();

        go.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (start.getSelectedItem() != null ||
                        stop.getSelectedItem() != null) {  // TODO: Change to 'if the list' is filled
                    Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                    String[] stops = {start.getSelectedItem().toString(),
                            stop.getSelectedItem().toString()};
                    intent.putExtra("start", stops[0]);
                    intent.putExtra("stop", stops[1]);
                    intent.putExtra("myId", myId);

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please select a starting and final stop with a bus route",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        location.setSelected(false);
        list.setVisibility(View.GONE);
        go.setVisibility(View.GONE);
    }

    protected void onLocate(View v) {
        if (v.isSelected()) {
            // TODO: Get location and compare to bus stop locations for the nearest bus stop
        }
    }

}

