package com.ajou.capstonedesign.museapplication;

public class LoginData {

    private String id;
    private String password;

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getPW() {
        return password;
    }

    public void setPW(String password) {
        this.password = password;
    }


    public static void setInstance(LoginData instance) {
        LoginData.instance = instance;
    }

    private static LoginData instance = null;

    public static synchronized LoginData getInstance(){
        if(null==instance){
            instance = new LoginData();
        }
        return instance;
    }

}
