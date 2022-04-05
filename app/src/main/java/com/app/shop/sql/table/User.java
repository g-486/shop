package com.app.shop.sql.table;

/**
 * create by 呵呵 on 2022/3/17.
 */
public class User {
    private int Uid;
    private String Uname;
    private String Pass;
    private String date;//时间精确到天
    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
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
