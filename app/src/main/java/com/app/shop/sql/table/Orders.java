package com.app.shop.sql.table;

/**
 * create by 呵呵 on 2022/3/17.
 */
public class Orders {
    private int Oid;
    private int uid;
    private double sumPrice;
    private float level;
    private String foods;
    private String date;  //下单时间   时间精确到分
    // 订单详情页显示  fragment
    private String other; //订单备注

    public Orders() {
    }

    public Orders(int oid, int uid, double sumPrice, float level, String foods, String date, String other) {
        Oid = oid;
        this.uid = uid;
        this.sumPrice = sumPrice;
        this.level = level;
        this.foods = foods;
        this.date = date;
        this.other = other;
    }

    public int getOid() {
        return Oid;
    }

    public void setOid(int oid) {
        Oid = oid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public double getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(double sumPrice) {
        this.sumPrice = sumPrice;
    }

    public float getLevel() {
        return level;
    }

    public void setLevel(float level) {
        this.level = level;
    }

    public String getFoods() {
        return foods;
    }

    public void setFoods(String foods) {
        this.foods = foods;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
