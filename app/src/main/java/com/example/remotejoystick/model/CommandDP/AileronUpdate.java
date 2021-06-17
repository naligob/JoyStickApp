package com.example.remotejoystick.model.CommandDP;

import com.example.remotejoystick.R;

import java.io.PrintWriter;

public class AileronUpdate extends Command{

    private final String m_specificCommand;

    public AileronUpdate(PrintWriter print, String partCommand, String specificCommand) {
        super(print, partCommand);
        this.m_specificCommand = specificCommand;
    }

    @Override
    public void execute(String data) {
        super.m_print.print(super.m_partCommand +
                                this.m_specificCommand +
                                    data +
                                        R.string.windows_line_separator);
        super.execute(data);
    }
}
