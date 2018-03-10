package com.example.sjw2g15.absolutebustard;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class BusStopListener implements AdapterView.OnItemSelectedListener {

    private Spinner start, stop;
    private View list;
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

        // TODO: Get buses that stop here
        // TODO: Put the buses in the list
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        list.setVisibility(View.GONE);
        go.setVisibility(View.GONE);
    }
}
