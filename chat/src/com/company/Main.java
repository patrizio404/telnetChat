package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final int DEFAULT_PORT = 10000;


    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        ServerSocket serverSocket = null;
        Socket socket = null;
        System.out.println("Server: port " + DEFAULT_PORT);

        // Start listenting
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                handler.start();
            }
        } catch (IOException ioe) {
            System.err.println("I/O Error...");
            System.err.println(ioe.getMessage());
        } finally {
            try {
                if(socket!=null) {
                    socket.close();
                }
                if(serverSocket!=null) {
                    serverSocket.close();
                }
            } catch (IOException ioe) {
                System.err.println("I/O Error...");
                System.err.println(ioe.getMessage());
            }
        }
    }
}
