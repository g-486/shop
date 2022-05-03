package com.app.shop.sql.table;

/**
 * create by 呵呵 on 2022/3/17.
 */
public class User {
    public int Uid;
    public String Uname;
    public String Pass;
    public String date;//时间精确到天

    public User() {
    }

    public User(int uid, String uname, String pass, String date) {
        Uid = uid;
        Uname = uname;
        Pass = pass;
        this.date = date;
    }
}
