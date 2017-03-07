package com.example.snerk.arduinoproject;

/**
 * Created by snerk on 2017-03-07.
 */

public class Client {

    String ipAdress;
    int port;

    public void connect(String ipAdress, int port){
        this.ipAdress = ipAdress;
        this.port = port;



    }
}