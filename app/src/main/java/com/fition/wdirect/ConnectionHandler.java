package com.fition.wdirect;

import java.net.Socket;

public class ConnectionHandler {
    private static Socket socket;

    public static synchronized Socket getSocket(){
        return socket;
    }

    public static synchronized void setSocket(Socket socket){
        ConnectionHandler.socket = socket;
    }
}
