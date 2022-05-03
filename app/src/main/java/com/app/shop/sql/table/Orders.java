package com.app.shop.sql.table;

/**
 * create by 呵呵 on 2022/3/17.
 */
public class Orders {
    public int Oid;
    public int Uid;
    public double sumPrice;
    public int level;
    public String foods;
    public String date;  //下单时间   时间精确到分
    // 订单详情页显示  fragment
    public String other; //订单备注

    public Orders() {
    }

    public Orders(int oid, int uid, double sumPrice, int level, String foods, String date, String other) {
        Oid = oid;
        Uid = uid;
        this.sumPrice = sumPrice;
        this.level = level;
        this.foods = foods;
        this.date = date;
        this.other = other;
    }
}
