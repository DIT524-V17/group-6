package com.example.snerk.arduinoproject;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    static String TAG = "MainActivity";
    public static Client client = new Client();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Application is running ^^");

        ImageButton turnRight = (ImageButton)(findViewById(R.id.turnRight));
        turnRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "Application is turning right ^^");
                client.sendCommands("turnRight");
                return true;
            }
        });

        ImageButton turnLeft = (ImageButton)(findViewById(R.id.turnLeft));
        turnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "Application is turning left ^^");
                client.sendCommands("turnLeft");
                return true;
            }
        });

        ImageButton forward = (ImageButton)(findViewById(R.id.driveForward));
        forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "Application is driving forward ^^");
                client.sendCommands("forwardDrive");
                return true;
            }
        });

        ImageButton backwards = (ImageButton)(findViewById(R.id.driveBackward));
        forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "Application is driving backwards ^^");
                client.sendCommands("driveBackward");
                return true;
            }
        });


    }


    public void Settings(View view) {

        startActivity(new Intent(getApplicationContext(), ControllerSettings.class));

    }
}
