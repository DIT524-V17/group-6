package com.example.snerk.arduinoproject;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;


public class MainActivity extends AppCompatActivity {
    static String TAG = "MainActivity";
    public static Client client = new Client();
    public static TextView distance;
    Thread t;
    @Override

    // Creating the instance and finding the diffrent buttons and texts from the XML File
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Application is running ^^");

        ImageButton turnRight = (ImageButton) (findViewById(R.id.turnRight));
        ImageButton turnLeft = (ImageButton) (findViewById(R.id.turnLeft));
        ImageButton forward = (ImageButton) (findViewById(R.id.driveForward));
        ImageButton backwards = (ImageButton) (findViewById(R.id.driveBackward));

        distance = (TextView) findViewById(R.id.distance);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        VideoView video = (VideoView) findViewById(R.id.videoView);
        String vidAdress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";
        Uri vidUri = Uri.parse(vidAdress);
        video.setVideoURI(vidUri);
        video.start();

        // Creates a new running thread that updates every second
         t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Log.i(TAG, "Running new thread");
                        Thread.sleep(100);
                        Log.i(TAG, "Reading sensor in new thread");
                        client.readSensor();
                    }
                } catch (InterruptedException e) {
                }
            }
        };




        // On Touch listener (makes sure you get one command when you hold the button down
        // and another when you release the button. For all 4 diretions.
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
                }
                return false;
            }
        });

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
                }
                return false;
            }
        });

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
                }
                return false;
            }
        });

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
                }
                return false;
            }
        });
    }

    // The button that connects you automatically to the predefined IP/Port. Intalizes the sensorReader
    public void autoConnect (View view) {
        Log.i(TAG, "Autoconnect");
        client.autoConnect();
        Log.i(TAG, "initialize sensor");
        client.sensorReader();
    }

    // Starts reading from the sensors by intilizing a new thread.
    public void startMeasure (View view) {
        Log.i(TAG, "Starting new threading");
        t.start();


    }

    // Takes you to the Settings View
    public void Settings(View view) {

        startActivity(new Intent(getApplicationContext(), ControllerSettings.class));

    }

}
