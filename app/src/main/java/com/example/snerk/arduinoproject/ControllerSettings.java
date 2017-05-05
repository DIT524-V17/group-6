package com.example.snerk.arduinoproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import static com.example.snerk.arduinoproject.MainActivity.client;

public class ControllerSettings extends AppCompatActivity {
    EditText ipAdress, port;
    Button connect;
    MainActivity main;
    ToggleButton toggleButton;

    private static final String TAG = "ControllerSettings";

    //Finds the various buttons in the XML-file
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_settings);

        ipAdress = (EditText) findViewById(R.id.ipAdress);
        port = (EditText) findViewById(R.id.port);
        connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(buttonConnectOnClickListener);
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    client.sendCommands("driveAuto");
                } else {
                    client.sendCommands("stopAuto");
                }
            }
        });
    }


    //Connects to the chosen IP/Port
    View.OnClickListener buttonConnectOnClickListener = new View.OnClickListener() {
        public void onClick(View arg0) {

            String ip = ipAdress.getText().toString();
            int lastPort = Integer.parseInt(port.getText().toString());
            Log.i(TAG, "Clicking on connect");
            client.connect(ip, lastPort);
           // main.playStream(ip);
        }


    };

    //Takes you Back
    public void Back(View view) {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }


}

