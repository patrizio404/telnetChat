package com.company.commands;


import com.company.CommandManager;
import com.company.Handler;
import com.company.User;

import java.io.IOException;

public class Quit implements Command {

        @Override
    public void run(Handler hdlr, String... args) {

        String username = hdlr.getUser().getUserName();
            hdlr.broadCastAllOthers(username + " is disconnected.");

            hdlr.respond("BYE");

        // Log out
        CommandManager.execute("/logout", hdlr);

        // Tell everyone you are leaving

    }

}
