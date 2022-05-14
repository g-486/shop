package com.app.shop.sql.table;

/**
 * create by 呵呵 on 2022/4/17.
 */
public class Say {
    private int sid;
    private int uid;
    private int gid;
    private int oid;
    private String datetime;
    private String str;  //评价内容

    public Say() {
    }

    public Say(int gid, int uid, int oid, String datatime, String str) {
        this.gid = gid;
        this.uid = uid;
        this.oid = oid;
        this.datetime = datatime;
        this.str = str;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
