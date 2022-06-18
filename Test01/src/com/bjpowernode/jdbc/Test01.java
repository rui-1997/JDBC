package com.bjpowernode.jdbc;

import java.sql.*;

public class Test01 {
    public static void main(String[] args) {
        Connection con=null;
        Statement stmt=null;
        ResultSet rs=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//加载驱动
            String url="jdbc:mysql://localhost:3306/bjpowernode";
            String user="root";
            String password="root";
            con= DriverManager.getConnection(url,user,password);//连接数据库
            //System.out.println(con);
            stmt=con.createStatement();//创建数据库操作对象
            //String sql="insert into dept(deptno,dname,loc) values (50,'人事部','北京') ";//注意：这里写的SQL语句不能加分号
            //String sql="update  dept set dname='资源部',loc='上海' where deptno=50";//注意：这里写的SQL语句不能加分号
            String sql="delete from dept  where deptno=50";//注意：这里写的SQL语句不能加分号
            int count = stmt.executeUpdate(sql);
            System.out.println(count==1?"操作成功":"操作失败");
            System.out.println("-------------------------\n遍历dept表");
            String sql1="select * from dept";
            rs=stmt.executeQuery(sql1);
            while(rs.next()){
                int deptno=rs.getInt("deptno");
                String dname=rs.getString("dname");
                String loc=rs.getString("loc");
                System.out.println(deptno+","+dname+","+loc);

            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(rs!=null){
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stmt!=null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(con!=null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
