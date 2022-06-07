package com.app.shop.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * create by 呵呵 on 2022/5/6.
 */
public class DButil {
    private static final String ClS="com.mysql.jdbc.Driver";
    private static final String URL="jdbc:mysql://192.168.1.37:3306/test?serverTimezone=UTC&userUnicode=true&charsetEncoding=utf-8";
    private static final String USER="shop_user";
    private static final String PASS="123456";

    private static Connection conn=null;
    private static ResultSet rs =null;
    private static PreparedStatement pst=null;
    //链接
    public static Connection getConn() throws Exception {
        Class.forName(ClS);
        conn=DriverManager.getConnection(URL,USER,PASS);
        return conn;
    }

    public static boolean test(){
        final boolean[] flag = {false};
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Statement st = getConn().createStatement();
//            pst=getConn().prepareStatement();
                    rs = st.executeQuery("select * from ;");
                    if (rs.next()) {
                        flag[0] =true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getMessage();
                }
            }
        }).start();
        return flag[0];
    }

    //Object...obj 可变长度数组
    //统一增 删 改
    public boolean update(String sql,Object...obj) {
        try {
            pst=getConn().prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                pst.setObject(i+1, obj[i]);
            }
            int row =pst.executeUpdate();
            if(row>0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }finally {
            try {
                getClose(conn, pst, rs);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

//    public List<T> query(String sql,Object...obj){
//        List<T> list =new ArrayList<>();
//        try {
//            pst=getConn().prepareStatement(sql);
//            for (int i = 0; i < obj.length; i++) {
//                pst.setObject(i+1, obj[i]);
//            }
//            rs =pst.executeQuery();
//            while(rs.next()) {
//                T t=entity(rs);
//                list.add(t);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                getClose(conn, pst, rs);
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return list;
//    }?

//    public abstract T entity(ResultSet rs) throws Exception;

    //关闭
    public void getClose(Connection conn,PreparedStatement pst,ResultSet rs){
        try {
            if(rs!=null) rs.close();
            if(pst!=null) pst.close();
            if(conn!=null) conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
