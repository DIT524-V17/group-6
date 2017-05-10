package com.example.snerk.arduinoproject;

import android.graphics.Color;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;
import android.os.Handler;
import android.widget.Toast;
import android.os.Message;


public class MainActivity extends AppCompatActivity {

    static String TAG = "MainActivity";
    public static boolean connected = false;
    public static Client client = new Client();
    private int backgroundColorVariator;
    Handler distanceHandler = new Handler();
    Thread t;
    TextView rightDistance;
    TextView frontDistance;
    TextView distance;
    MediaController mediaController;
    VideoView video;


    Handler handler = new Handler(){

        public void handleMessage(Message msg) {
            distance = (TextView) findViewById(R.id.distance);
            distance.setText(client.distance);
            frontDistance = (TextView) findViewById(R.id.frontdistance);
            frontDistance.setText(client.distance);
            rightDistance = (TextView) findViewById(R.id.rightdistance);
            rightDistance.setText(client.distance);

        }
    };
    // Creating the instance and finding the diffrent buttons and texts from the XML File
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Application is running ^^");

        ImageButton turnRight = (ImageButton) (findViewById(R.id.turnRight));
        ImageButton turnLeft = (ImageButton) (findViewById(R.id.turnLeft));
        ImageButton forward = (ImageButton) (findViewById(R.id.driveForward));
        ImageButton backwards = (ImageButton) (findViewById(R.id.driveBackward));
        ImageButton tankLeft = (ImageButton) (findViewById(R.id.tankLeft));
        ImageButton tankRight = (ImageButton) (findViewById(R.id.tankRight));
        turnRight.setBackgroundResource(0);
        turnLeft.setBackgroundResource(0);
        forward.setBackgroundResource(0);
        backwards.setBackgroundResource(0);
        tankLeft.setBackgroundResource(0);
        tankRight.setBackgroundResource(0);

        distance = (TextView) findViewById(R.id.distance);
        frontDistance = (TextView) findViewById(R.id.frontdistance);
        rightDistance = (TextView) findViewById(R.id.rightdistance);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        video = (VideoView) findViewById(R.id.videoView);
        //mediaController.setVisibility(View.GONE);


        // Creates a new running thread that updates every second
        Runnable R = new Runnable() {
            @Override
            public void run() {
                try {
                    while (!t.isInterrupted()) {

                        Log.i(TAG, "Running new thread");

                        Log.i(TAG, "Reading sensor in new thread");


                        //client.sendCommands("sensorRead:\n");
                        client.readSensor();
                        Thread.sleep(50);
                        Log.i(TAG, client.distance);
                        runOnUiThread(new Runnable() {
                            public void run(){
                                try {
                                    if (!client.distance.isEmpty()) {
                                        if (client.distance.startsWith("center")) {
                                            String obstacle= client.distance.substring(client.distance.indexOf(':') + 1);
                                            Log.i(TAG, obstacle);
                                            frontDistance.setText(obstacle);
                                            setBackgroundColor(Integer.parseInt(obstacle), frontDistance);
                                            frontDistance.invalidate();
                                            Log.i(TAG, "obstacle on the front detected ");

                                        } else if (client.distance.startsWith("right")) {
                                            String obstacle= client.distance.substring(client.distance.indexOf(':') + 1);
                                            Log.i(TAG, obstacle);
                                            rightDistance.setText(obstacle);
                                            setBackgroundColor(Integer.parseInt(obstacle), rightDistance);
                                            rightDistance.invalidate();
                                            Log.i(TAG, "obstacle on the right detected ");

                                        } else if (client.distance.startsWith("left")) {
                                            String obstacle= client.distance.substring(client.distance.indexOf(':') + 1);
                                            Log.i(TAG, obstacle);
                                            distance.setText(obstacle);
                                            setBackgroundColor(Integer.parseInt(obstacle), distance);
                                            distance.invalidate();
                                            Log.i(TAG, "obstacle on the left detected ");
                                        }
                                    }
                                }catch(Exception e){
                                    Log.i(TAG, e.toString());
                                }


                            }
                        });

                    }
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };
        t =new Thread(R);

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
        startVideo(); 
        Log.i(TAG, "Starting new threading");
        t.start();
    }

    public void startVideo(){
        Log.i(TAG, "Starting The Videofeed");
        Uri UriSrc = Uri.parse("http://192.168.42.1:8090");
        if (UriSrc == null) {
            Toast.makeText(MainActivity.this,
                    "UriSrc == null", Toast.LENGTH_LONG).show();
        } else {
            video.setVideoURI(UriSrc);
            mediaController = new MediaController(this, false);
            video.setMediaController(mediaController);
            video.start();

            Toast.makeText(MainActivity.this,
                    "Connect: " + "http://192.168.42.1:8090",
                    Toast.LENGTH_LONG).show();
        }
    }
    // Takes you to the Settings View
    public void Settings(View view) {

        startActivity(new Intent(getApplicationContext(), ControllerSettings.class));

    }

    public void setBackgroundColor(int distanceInt, TextView view){
        if (distanceInt > 50){
            distance.setBackgroundColor(Color.GREEN);
        }

        else if (20 < distanceInt && distanceInt < 50){
            distance.setBackgroundColor(Color.YELLOW);
        }

        else if (20 > distanceInt) {
            distance.setBackgroundColor(Color.RED);

        }
    }
}