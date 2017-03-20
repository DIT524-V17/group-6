package com.example.snerk.arduinoproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ControllerSettings extends AppCompatActivity {
    EditText ipAdress, port;
    Button connect;
    Client client = new Client();
    private static final String TAG = "ControlerSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller_settings);


        ipAdress = (EditText) findViewById(R.id.ipAdress);
        port = (EditText) findViewById(R.id.port);
        connect = (Button)findViewById(R.id.connect);
        connect.setOnClickListener(buttonConnectOnClickListener);
    }
////

    View.OnClickListener buttonConnectOnClickListener = new View.OnClickListener(){
        public void onClick(View arg0) {

            String ip =  ipAdress.getText().toString();
            int lastPort = Integer.parseInt(port.getText().toString());
            Log.i(TAG, "Clicking on connect");
            client.connect(ip, lastPort);
        }


    };




    public void Back(View view) {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }
}
//      }