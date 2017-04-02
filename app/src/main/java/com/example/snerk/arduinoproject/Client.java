package com.example.snerk.arduinoproject;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {


    // Initalize private variables.
    private String staticIp = "192.168.42.1";
    private int staticPort = 1337;
    private PrintStream printStream;
    private Socket socket;
    private BufferedReader bufferedReader;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private static final String TAG = "Client";
    private String distance;


    //Connecting to the RaspberryPI-server
    public void connect(String ipAdress, int port) {
        try {


            //Opening socket and making sure you can send data
            socket = new Socket(ipAdress, port);
            OutputStream outputStream = socket.getOutputStream();
            this.printStream = new PrintStream(outputStream);

            Log.i(TAG, "done connecting");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
         //   e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
        }
    }

    // Using the same method as above, but with predefined IP
    public void autoConnect() {
         connect(staticIp, staticPort);
        }


    // Making sure the app is ready to read data from the sensors(and possibly other data)
    public void sensorReader()
    {
        try {
            Log.i(TAG, "wants to read from arduino");
            this.inputStream = socket.getInputStream();
            this.inputStreamReader = new InputStreamReader(inputStream);
            Log.i(TAG, "inputstream ready");
            this.bufferedReader = new BufferedReader(inputStreamReader);
            Log.i(TAG, "buffered reader reeady");
        }
        catch (IOException e){
        e.printStackTrace();
        }
    }

    //Reading from the sensors and updating the TextView "Distance" in the Main View
    public void readSensor()
    {
        try {
            String line = this.bufferedReader.readLine();
            if (line != null && line != "0") {
                Log.i(TAG, line);
                MainActivity.distance.setText(distance);
            }
        }
          catch (IOException e){
        e.printStackTrace();
        }
    }

    // Seding commands to the Ardunio via the PI
    public void sendCommands(String command) {
        printStream.print(command);

    }

    // Closing the socket
    public void disconnect() {
        try {
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
