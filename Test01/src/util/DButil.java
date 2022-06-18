package util;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * 该类是JDBC技术的工具库
 *  1.connection方法是连接数据库，输入参数是xxx.properties配置文件的路径，该文件必须放在类路径下，即src下
 *  2.close方法是释放资源方法
 */

public class DButil {
    private DButil(){};
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static Connection connection(String propertiesPath) throws SQLException {
        ResourceBundle bundle=ResourceBundle.getBundle(propertiesPath);//利用资源绑定器获取薪资
        String url=bundle.getString("url");
        String username=bundle.getString("username");
        String password=bundle.getString("password");
        return  DriverManager.getConnection(url,username,password);
    }
    public static void close(Connection conn, Statement stem, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stem!=null){
            try {
               stem.close();
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
}
