package com.company.commands;


import com.company.Handler;
import com.company.User;

import java.io.IOException;

public class Login implements Command {


    private void init(Handler hdlr) {
        hdlr.respond("Insert UserName");
    }

    @Override
    public void run(Handler hdlr, String... args) {

            String line = null;

            try {
                if(args.length>1) {
                    line = args[1];
                }else {
                    init(hdlr);
                    line = hdlr.getIn().readLine();
                }
            } catch (IOException ex) {
                System.err.println("I/O Error...");
                System.err.println(ex.getMessage());
            }

            // Don't proceed if '/quit' signal received
            if(line!=null) {

                // Initialize user
                User user = new User();
                user.setUserName(line);

                user.setHandler(hdlr);
                hdlr.setUser(user);
                hdlr.getUsers().put(user.getUserName(), user);

                // Greeting connected user
                hdlr.respond("Hello " + user.getUserName() + "!");
                hdlr.broadCastAllOthers(user.getUserName() + " has joined conversation");
            }
        }
}
