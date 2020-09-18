package com.company;


import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Handler for the client connection. Could be also considered
 * as client manager. Manages the connections to the server,
 * holds information about connected users and rooms. And manages the I/O
 * between the client and server.
 *
 */
public class Handler extends Thread {

    // Available connections
    private static final Set<Handler> hdlrs
            = Collections.synchronizedSet(new HashSet());
    // Connected users
    private static final Map<String, User> users = Collections.synchronizedMap(new HashMap());

    public Map<String, User> getUsers() {
        return users;
    }

    private final Socket socket;
    // Stream input
    private final BufferedReader in;
    // Stream output
    private final PrintWriter out;
    // The connected user
    private User user;

    public BufferedReader getIn() {
        return in;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Handler(Socket socket) throws IOException {

        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }


    @Override
    public void run() {
        hdlrs.add(this);

        // Load disposable commands
        CommandManager.init();

        try {
            // Greeting message
            respond("Welcome to the chat");
            // Force first time login
            CommandManager.execute(CommandManager.COMMAND_LOGIN, this);


            // Stream input line
            String line;
            // Accepts input till '/exit' signal is received
            while (!(line = in.readLine())
                    .equalsIgnoreCase(CommandManager.COMMAND_QUIT)) {

                if (!line.startsWith("/")) {
                    line = "/send " + line;
                }

                //Get commands and arguments
                String[] args = line.split(" ");

                CommandManager.execute(args[0], this, args);
            }
            CommandManager.execute(CommandManager.COMMAND_QUIT,this);
        }catch (IOException ioe) {
            System.err.println("I/O Error...");
            System.err.println(ioe.getMessage());
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ioe) {
                System.err.println("I/O Error...");
                System.err.println(ioe.getMessage());
            } finally {
                if(user!=null) {

                    users.remove(user.getUserName());
                    user = null;
                    hdlrs.remove(this);
                }
            }
        }

    }


    /**
     * Handler Response Manager
     * Respond to the client.
     *
     * @param text Message to respond with.
     */
    public void respond(String text) {

        this.out.print(text);
        this.out.flush();
    }

    /**
     * Send message to all others connected users
     * @param msg
     */
    public void broadCastAllOthers(String msg) {
        synchronized (users) {
            for (User u : users.values()) {
                if (user != u) {
                    u.getHandler().respond(msg);
                }
            }
        }
    }

    public void broadcastAll(String msg) {

        synchronized (hdlrs) {
            for(Handler handler: hdlrs) {
                handler.respond(msg);
            }
        }
    }
}
