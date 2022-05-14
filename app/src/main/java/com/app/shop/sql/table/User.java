package com.app.shop.sql.table;

/**
 * create by 呵呵 on 2022/3/17.
 */
public class User {
    private int uid;
    public String uname;
    public String Pass;
    public String date;//时间精确到天

    public User() {
    }

    public User(String uname) {
        this.uname = uname;
    }

    public User(int uid, String uname, String pass, String date) {
        this.uid = uid;
        this.uname = uname;
        Pass = pass;
        this.date = date;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
