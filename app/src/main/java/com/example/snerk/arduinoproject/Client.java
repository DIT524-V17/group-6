package com.example.snerk.arduinoproject;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

    private PrintStream printStream;
    private Socket socket;
    private static final String TAG = "Client";

    public void connect(String ipAdress, int port) {
        Log.i(TAG, "connecting and shit");
        try {
            //socket.setSoTimeout(4000);
            Log.i(TAG, "connecting and shit socket");
            socket = new Socket(ipAdress, port);

            Log.i(TAG, "connecting and shit2");
            OutputStream outputStream = socket.getOutputStream();
            Log.i(TAG, "connecting and shit3");
            this.printStream = new PrintStream(outputStream);
            Log.i(TAG, "connecting and shit4");
           // printStream.print("carl is an idiot");
      
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
         //   e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
           // e.printStackTrace();
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
