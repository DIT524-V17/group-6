package com.example.snerk.arduinoproject;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by snerk on 2017-03-07.
 */

public class Client {

    private String ipAdress;
    private int port;
    private PrintStream print;
    private Socket socket;
    private static final String TAG = "Client";

    public void connect(String ipAdress, int port) {
        this.ipAdress = ipAdress;
        this.port = port;
        Log.i(TAG, "connecting and shit");
        try {
            this.socket = new Socket(ipAdress, port);

            OutputStream outputStream = socket.getOutputStream();
            this.print = new PrintStream(outputStream);
            Log.i(TAG, "connecting and shit");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void sendCommands(String command) {
        print.print(command);

    }

    public void disconnect() {
        try {
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}