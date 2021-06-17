package com.example.remotejoystick.model;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.remotejoystick.R;
import com.example.remotejoystick.model.CommandDP.*;


import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FGClient {

    @Nullable
    String m_ip;
    int m_port;

    private Socket clientSocket;
    private PrintWriter out;
    private boolean isConnected = false;

    public FGClient(String ip, int port) {
        this.m_ip=ip;
        this.m_port=port;

    }

    @Nullable
    public String getM_ip() {
        return m_ip;
    }

    public void setM_ip(@Nullable String ip) {
        this.m_ip = ip;
    }

    public int getM_port() {
        return this.m_port;
    }

    public void setM_port(int port) {
        this.m_port = port;
    }

    public void setWriterForMap(HashMap<String, Command> map){
        if (isConnected) {
            for (Map.Entry<String, Command> entry:map.entrySet()) {
                entry.getValue().setM_print(this.out);
            }
        }
    }

    public void Connect() {
        try {
            clientSocket = new Socket(m_ip,m_port);
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            isConnected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String data , Command command) {
        if (isConnected) {
            command.execute(data);
        }
        else {
            Log.d("ERROR", "send: no connecting occur");
        }
    }
    public void close(){
        try {
            this.clientSocket.close();
            this.out.close();
            this.isConnected = false;
        }
        catch (IOException e){
            Log.d("ERROR", "close: failed to close");
        }

    }
    private String getString(int convert){
        return String.valueOf(convert);
    }
}
