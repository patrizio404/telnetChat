package com.company.commands;

import com.company.Handler;

public interface Command {


    public void run(Handler hdlr, String... args);
}
