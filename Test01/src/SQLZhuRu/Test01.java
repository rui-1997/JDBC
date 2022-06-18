package SQLZhuRu;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 演示SQL注入：
 * 请输入用户名：
 * dsa
 * 请输入密码：
 * dsa ' or '1'='1
 * 登录成功
 * 原因是将密码中的or当成了SQL语句中的关键字，实际执行的SQL语句变成了下面的语句
 * select * from t_user where username='dsa' and password='dsa ' or '1'='1';
 * 这样后果就是'1'='1'成立，select选择了表中的所有数据，这样导致ResultSet中也有了值，因此判定登录成功了。
 *
 */

public class Test01 {
    public static void main(String[] args) {
        //初始化一个界面，返回读到的数据
        Map<String,String> userLoginInfo=initUI();
        //验证用户名和密码
        boolean loginResult=login(userLoginInfo);
        System.out.println(loginResult?"登录成功":"登录失败");
    }

    private static boolean login(Map<String,String> userLoginInfo) {
        boolean isOk=false;//是否登录成功标识
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//注册驱动
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","root");//连接数据库
            stmt=conn.createStatement();//创建数据库操作对象
            String sql="select * from t_user where username='"+userLoginInfo.get("username")+"' and password='"+userLoginInfo.get("password")+"'";
            rs=stmt.executeQuery(sql);
            if(rs.next()){
                isOk=true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
        return isOk;
    }

    private static Map<String, String> initUI() {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入用户名：");
        String username=sc.nextLine();
        System.out.println("请输入密码：");
        String password=sc.nextLine();
        Map<String,String> userLoginInfo=new HashMap<>();
        userLoginInfo.put("username",username);
        userLoginInfo.put("password",password);
        return userLoginInfo;
    }
}
