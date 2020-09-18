package com.company.commands;


import com.company.Handler;
import com.company.User;

import java.io.IOException;

public class Logout implements Command {

    @Override
    public void run(Handler hdlr, String... args) {

        if(hdlr.getUser()!=null) {
            // Leave group
            hdlr.getUsers().remove(hdlr.getUser().getUserName());
            hdlr.setUser(null);
        }
    }
}
