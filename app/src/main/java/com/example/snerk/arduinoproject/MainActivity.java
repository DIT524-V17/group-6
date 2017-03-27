package com.example.snerk.arduinoproject;

import android.content.Intent;
import android.media.Image;
import android.os.StrictMode;
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

        ImageButton turnRight = (ImageButton) (findViewById(R.id.turnRight));
        ImageButton turnLeft = (ImageButton) (findViewById(R.id.turnLeft));
        ImageButton forward = (ImageButton) (findViewById(R.id.driveForward));
        ImageButton backwards = (ImageButton) (findViewById(R.id.driveBackward));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        forward.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
                    client.sendCommands("forward:\n");
                } else if (action == MotionEvent.ACTION_UP) {
                    Log.i("repeatBtn", "MotionEvent.ACTION_UP");
                    client.sendCommands("stop:\n");
                }//end else
                return false;
            } //end onTouch
        }); //end b my button

    }

    public void turnRight(View view) {

        Log.i(TAG, "Application is turning right ^^");
        client.sendCommands("turnRight:\n");
    }
    public void turnLeft(View view) {

        Log.i(TAG, "Application is turning left ^^");
        client.sendCommands("turnLeft:\n");
    }
   /* public void forward(View view) {

        Log.i(TAG, "Application is turning forward ^^");
        client.sendCommands("forward:\n");
    }*/

    public void backward(View view) {

        Log.i(TAG, "Application is turning backward ^^");
        client.sendCommands("backwards:\n");
    }

    public void autoConnect (View view) {
        client.autoConnect();
    }

    public void Settings(View view) {

        startActivity(new Intent(getApplicationContext(), ControllerSettings.class));

    }

}
