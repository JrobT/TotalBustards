package com.example.sjw2g15.absolutebustard;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by User on 10/03/2018.
 */

public class TClickListener implements View.OnClickListener {
    final EditText msg;
    Button send;
    final TextView convo;
    final TextView status;

    ClientConThread t;

    public TClickListener(EditText m, TextView c, TextView s, ClientConThread t){
        msg = m;
        convo = c;
        status = s;

        this.t = t;
    }

    public void onClick(View v) {
        String message = msg.getText().toString();

        status.setText("...");
        PrintWriter outp = null;
        BufferedReader inp = null;
        //status.setText("Established connection..");
        String serverMsg = null;

        t.sendMessage(message);

        //else
        //    status.setText("Problem in connection..!");
    }
}
