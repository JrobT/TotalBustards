package com.example.sjw2g15.absolutebustard;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Listener for selection of starting bus stop and final bus stop fields in ui
 */
public class BusStopListener implements AdapterView.OnItemSelectedListener {

    private Spinner start, stop;    // selection fields
    private View list;  // list of bus routes that run between the two selected stops
    private View go;

    public BusStopListener(Spinner start, Spinner stop, View list, View go) {
        this.start = start;
        this.stop = stop;
        this.list = list;
        this.go = go;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (start.getSelectedItem() != stop.getSelectedItem()) {
            list.setVisibility(View.VISIBLE);
            go.setVisibility(View.VISIBLE);
        } else {
            list.setVisibility(View.GONE);
            go.setVisibility(View.GONE);
        }

        // TODO: Get bus routes
        // TODO: Put the bus routes in the list
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        list.setVisibility(View.GONE);
        go.setVisibility(View.GONE);
    }
}
