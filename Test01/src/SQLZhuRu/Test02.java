package SQLZhuRu;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 解决SQL注入问题：
 *    关键就是将输入的信息不进行编译，这样即使输入了关键字，不编译也不会执行
 *    因此用到了PreparedStatement预编译操作对象
 *
 * 演示效果
 * 请输入用户名：
 * fjlk
 * 请输入密码：
 * djfk ' or '1' ='1
 * 登录失败
 */

public class Test02 {
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
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");//注册驱动
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/bjpowernode","root","root");//连接数据库
            String sql="select * from t_user where username=? and password=?";//？通配符对要检索的用户名和密码预先占位
            ps=conn.prepareStatement(sql);//预编译数据库操作对象
            //给通配符赋值，1代表第一个通配符，setString代表赋的值是String类型，想赋什么类型的值，就set什么。
            ps.setString(1,userLoginInfo.get("username"));
            ps.setString(2,userLoginInfo.get("password"));
            rs=ps.executeQuery();
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
            if(ps!=null){
                try {
                    ps.close();
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
