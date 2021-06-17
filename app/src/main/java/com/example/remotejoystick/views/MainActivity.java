package com.example.remotejoystick.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.example.remotejoystick.R;
import com.example.remotejoystick.model.CommandDP.*;
import com.example.remotejoystick.model.FGClient;

import java.io.PrintWriter;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FGClient fg = new FGClient("10.100.102.11",6400);
        HashMap<String, Command> map = new HashMap<>();
        initializeMap(map);
        new Thread(() ->{
            fg.Connect();
            fg.setWriterForMap(map);
            SystemClock.sleep(5000);
            fg.close();
        }).start();
    }

    private void initializeMap(HashMap<String, Command> map){
        String partCommand = getString(R.string.part_command);
        ThrottleUpdate thr = new ThrottleUpdate(null, partCommand,getString(R.string.throttle_addition));
        map.put("throttle",thr);
        RudderUpdate rud = new RudderUpdate(null, partCommand,getString(R.string.rudder_addition));
        map.put("rudder",rud);
        ElevatorUpdate ele = new ElevatorUpdate(null, partCommand,getString(R.string.elevator_addition));
        map.put("elevator",ele);
        AileronUpdate ail = new AileronUpdate(null, partCommand,getString(R.string.aileron_addition));
        map.put("aileron",ail);
    }
}