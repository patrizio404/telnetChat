package com.company.commands;


import com.company.Handler;
import com.company.User;

import java.io.IOException;

public class Send implements Command {

    @Override
    public void run(Handler hdlr, String... args) {

        if(args.length== 1) {
            hdlr.respond("Nothing to say ?");
            return;
        }
        else{
            String[] text = new String[args.length-1];
            for(int i =0; i<args.length-1;i++)
                text[i]=args[i+1];
            hdlr.broadCastAllOthers("- "
                    + hdlr.getUser().getUserName()
                    + ": "
                    + String.join(" ",text));
        }
    }
}
