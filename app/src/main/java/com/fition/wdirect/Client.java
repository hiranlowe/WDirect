package com.fition.wdirect;

import android.content.Intent;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client extends Thread {
    private static final int PORT = 5682;
    Socket socket;
    String hostAddress;
    private MainActivity mActivity;

    Client(InetAddress address, MainActivity mActivity) {
        this.mActivity = mActivity;
        this.socket = new Socket();
        this.hostAddress = address.getHostAddress();
    }

    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress(hostAddress, PORT), 500);

            ConnectionHandler.setSocket(socket);

            mActivity.startActivity(new Intent(mActivity, TalkingActivity.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
