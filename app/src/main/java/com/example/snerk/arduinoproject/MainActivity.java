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
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    static String TAG = "MainActivity";
    public static Client client = new Client();
    //public TextView distance = (TextView) findViewById(R.id.distance);

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

        //VideoView video = (VideoView) findViewById(R.id.videoView);
        //video.start();

        String distance = "9";
        TextView textView = (TextView) findViewById(R.id.distance);
        textView.setText(distance);

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
        turnRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
                    client.sendCommands("turnRight:\n");
                } else if (action == MotionEvent.ACTION_UP) {
                    Log.i("repeatBtn", "MotionEvent.ACTION_UP");
                    client.sendCommands("stop:\n");
                }//end else
                return false;
            } //end onTouch
        }); //end b my button
        turnLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
                    client.sendCommands("turnLeft:\n");
                } else if (action == MotionEvent.ACTION_UP) {
                    Log.i("repeatBtn", "MotionEvent.ACTION_UP");
                    client.sendCommands("stop:\n");
                }//end else
                return false;
            } //end onTouch
        }); //end b my button
        backwards.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionevent) {
                int action = motionevent.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    Log.i("repeatBtn", "MotionEvent.ACTION_DOWN");
                    client.sendCommands("backwards:\n");
                } else if (action == MotionEvent.ACTION_UP) {
                    Log.i("repeatBtn", "MotionEvent.ACTION_UP");
                    client.sendCommands("stop:\n");
                }//end else
                return false;
            } //end onTouch
        }); //end b my button
    }

    public void autoConnect (View view) {

        client.autoConnect();
    }

    public void Settings(View view) {

        startActivity(new Intent(getApplicationContext(), ControllerSettings.class));

    }

}
