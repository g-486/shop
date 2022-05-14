package com.app.shop.sql.table;

/**
 * create by 呵呵 on 2022/3/17.
 */
public class Goods {
    private int gid;
    private String gname;
    private double price;
    private String desc;  //商品描述
    private String type;   //商品种类
    private String image; //图片存储路径
    private String weight;  //分量
    private String taste;   //口味类型
    private int salenum;  //销售量

    public Goods() {
    }

    public Goods(String gname, double price, String desc, String type, String weight, String taste) {
        this.gname = gname;
        this.price = price;
        this.desc = desc;
        this.type = type;
        this.weight = weight;
        this.taste = taste;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public int getSalenum() {
        return salenum;
    }

    public void setSalenum(int salenum) {
        this.salenum = salenum;
    }
}
