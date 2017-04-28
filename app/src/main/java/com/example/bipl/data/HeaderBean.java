package com.example.bipl.data;

/**
 * Created by fahad on 4/28/2017.
 */

public class HeaderBean {
    private String uname;
    private String pass;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "HeaderBean{" +
                "uname='" + uname + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
