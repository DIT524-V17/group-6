package com.example.snerk.arduinoproject;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
    private String staticIp = "192.168.42.1";
    private int staticPort = 1337;
    private PrintStream printStream;
    private Socket socket;
    private static final String TAG = "Client";

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
        try {
            Log.i(TAG, "autoConnecting");
            socket = new Socket(staticIp, staticPort);

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
