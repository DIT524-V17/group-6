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

    private String staticIp = "192.168.42.1";
    private int staticPort = 1337;
    private PrintStream printStream;
    private Socket socket;
    private BufferedReader bufferedReader;
    private static final String TAG = "Client";
    private boolean measureDistance = true;
    String distance;

    public void connect(String ipAdress, int port) {
        try {

            Log.i(TAG, "opening socket");
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

    public void autoConnect() {
         connect(staticIp, staticPort);
        }

    public void setRunning(boolean running){
        this.measureDistance = true;
    }

    public void arduinoReader()
    {
        try {
            Log.i(TAG, "wants to read from arduino");
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            Log.i(TAG, "inputstream ready");
            bufferedReader = new BufferedReader(inputStreamReader);
            Log.i(TAG, "buffered reader reeady");
            while(measureDistance){
                //bufferedReader block the code
                String line = bufferedReader.readLine();
                if(line != null){
                    Log.i(TAG, line);
                 //   this.distance = line;
                }
            }


            }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendCommands(String command) {
        printStream.print(command);

    }

    public void disconnect() {
        try {
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
