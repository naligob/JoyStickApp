package com.example.remotejoystick.model.CommandDP;

import androidx.annotation.Nullable;

import com.example.remotejoystick.R;

import java.io.PrintWriter;

public abstract class Command implements CommandOperation {
    protected PrintWriter m_print;
    protected String m_partCommand;

    public Command(@Nullable PrintWriter print, String partCommand) {
        this.m_print = print;
        this.m_partCommand = partCommand;
    }
    @Override
    public void execute(String data) {
        m_print.flush();
    }

    public void setM_print(PrintWriter pw){
        this.m_print = pw;
    }
}


