package com.example.remotejoystick.model;

import androidx.annotation.Nullable;

public class Utils {
    public static boolean valid_IP_Port(@Nullable String ip,int port){
        return port == 6400 && ip != null;
    }
}
