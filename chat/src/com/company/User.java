package com.company;

public class User {


    // User Name
    private String userName;
    // User handler (client)
    private Handler handler;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
