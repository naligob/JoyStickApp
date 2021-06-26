package com.example.remotejoystick.model;
import android.util.Log;

import com.example.remotejoystick.R;


import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//10.100.102.6

public class AppModel {

    private String mIP;
    private int mPort;

    private ExecutorService mExecutor;
    private Socket mClientSocket;
    private PrintWriter mOut;
    private boolean mIsConnected = false;

    public AppModel() {
        this.mExecutor = Executors.newFixedThreadPool(1);
        this.mIsConnected = false;
        this.mIP = null;
        this.mPort = -1;
    }

    public String getIP() {
        return mIP;
    }

    public void setIP(String ip) {
        this.mIP = ip;
    }

    public int getPort() {
        return this.mPort;
    }

    public void setPort(int port) {
        this.mPort = port;
    }

    public boolean getmIsConnected(){
        return this.mIsConnected;
    }

    public void connect() {
        if (!mIsConnected) {
            mExecutor.execute(() -> {
                try {
                    mClientSocket = new Socket(mIP, mPort);
                    mOut = new PrintWriter(mClientSocket.getOutputStream(), true);
                    mIsConnected = true;
                } catch (UnknownHostException e) {
                    Log.d("AppModel", "connect:Socket: " + e.toString());
                } catch (IOException e) {
                    Log.d("AppModel", "connect:PrintWriter: " + e.toString());
                }
            });
        } else {
            Log.d("AppModel", "connect: already connected");
        }
    }

    public void sendThr(String data) {
        if (mIsConnected) {
            mExecutor.execute(() -> {
                try {
                    mOut.print("set /controls/engines/current-engine/throttle " + data + "\r\n");
                    mOut.flush();
//                    Log.d("AppModel", "sendThr: " + data);
                } catch (Exception e) {
//                    Log.d("AppModel", "sendThr: " + e.toString());
                }
            });
        } else {
            Log.d("AppModel", "sendThr: no connecting occur");
        }
    }

    public void sendRud(String data) {
        if (mIsConnected) {
            mExecutor.execute(() -> {
                try {
                    mOut.print("set /controls/flight/rudder " + data + "\r\n");
                    mOut.flush();
//                    Log.d("AppModel", "sendRud: " + data);
                } catch (Exception e) {
                    Log.d("AppModel", "sendRud: " + e.toString());
                }
            });
        } else {
            Log.d("AppModel", "sendRud: no connecting occur");
        }
    }

    public void sendJoyStickUpdate(String dataAil, String dataEle) {
        if (mIsConnected) {
            mExecutor.execute(() -> {
                try {
                    mOut.print("set /controls/flight/aileron " + dataAil + "\r\n");
                    mOut.flush();
                    mOut.print("set /controls/flight/rudder " + dataEle + "\r\n");
                    mOut.flush();
//                    Log.d("AppModel", "sendJoyStickUpdate: ali : " + dataAil + "ele : " + dataEle);
                } catch (Exception e) {
                    Log.d("AppModel", "sendJoyStickUpdate: " + e.toString());
                }
            });
        } else {
            Log.d("AppModel", "sendJoyStickUpdate: no connecting occur");
        }
    }

    public void close() {
        try {
            this.mClientSocket.close();
            this.mOut.close();
            this.mIsConnected = false;
            this.mExecutor.shutdown();
        } catch (Exception e) {
            Log.d("AppModel", "close: IO failed to close or already closed");
            Log.d("AppModel", e.toString());
        }
    }
}
