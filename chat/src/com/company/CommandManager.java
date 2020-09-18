package com.company;

import com.company.commands.Command;

import java.util.HashMap;

public class CommandManager {


    public static final String COMMAND_LOGIN = "/login";
    public static final String COMMAND_QUIT = "/quit";
    public static final String COMMAND_SEND = "/send";
    public static final String COMMAND_LOGOUT = "/logout";


    private static String[] cPlugins = {
             COMMAND_LOGIN,COMMAND_QUIT,COMMAND_SEND,COMMAND_LOGOUT
    };


    public static String[] getCComands() {
        return cPlugins;
    }

    private static HashMap<String, String> commands = new HashMap();

    public static HashMap<String, String> getCommands() {
        return commands;
    }


    public static void init() {

        for(int i=0; i<cPlugins.length; i++) {
            String command = getCComands()[i].toLowerCase();
            String cls = "com.company.commands."
                    + command.substring(1, 2).toUpperCase()
                    + command.substring(2);
            commands.put(command, cls);
        }
    }


    public static void execute(String command, Handler hdlr, String... args) {


        String plugin = commands.get(command.toLowerCase());
        if(plugin==null) {
            hdlr.respond("Unrecognized command '" + command + "'.");
        }else {
            ClassLoader classLoader = Command.class.getClassLoader();

            try {
                Class aClass = classLoader.loadClass(plugin);
                Command c = (Command) aClass.newInstance();
                c.run(hdlr, args);
            } catch (ClassNotFoundException ex) {
                hdlr.respond("~LI~FRPlugin '" + plugin + "' load failure.~RS");
            } catch (InstantiationException ex) {
                hdlr.respond("~LI~FRPlugin '" + plugin + "' load failure.~RS");
            } catch (IllegalAccessException ex) {
                hdlr.respond("~LI~FRPlugin '" + plugin + "' load failure.~RS");
            }
        }
    }
}
