package com.app.shop.sql.table;

/**
 * create by 呵呵 on 2022/3/17.
 */
public class Goods {
    public int Gid;
    public String Gname;
    public double price;
    public String desc;  //商品描述
    public String type;   //商品种类
    public String image; //图片存储路径
    public String weight;  //分量
    public String taste;   //口味类型
    public int salenum;  //销售量

    public Goods() {
    }

    public Goods(int gid, String gname, double price, String desc, String type, String image, String weight, String taste, int salenum) {
        Gid = gid;
        Gname = gname;
        this.price = price;
        this.desc = desc;
        this.type = type;
        this.image = image;
        this.weight = weight;
        this.taste = taste;
        this.salenum = salenum;
    }
}
