package com.app.shop.sql.table;

/**
 * create by 呵呵 on 2022/4/17.
 */
public class Say {
    public int sid;
    public int uid;
    public int gid;
    public int oid;
    public String datatime;
    public String str;  //评价内容

    public Say() {
    }

    public Say(int sid, int gid, int uid, int oid, String datatime, String str) {
        this.sid = sid;
        this.gid = gid;
        this.uid = uid;
        this.oid = oid;
        this.datatime = datatime;
        this.str = str;
    }
}
