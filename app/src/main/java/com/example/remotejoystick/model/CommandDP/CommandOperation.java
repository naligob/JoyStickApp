package com.example.remotejoystick.model.CommandDP;

import java.io.PrintWriter;

@FunctionalInterface
public interface CommandOperation {
    void execute(String data);
}
