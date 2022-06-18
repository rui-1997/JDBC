package ShowHangJiSuo;

import util.DButil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 在ShiWu01为结束事务时，该程序执行不了，一直会处于等待状态，因为update的数据被ShiWu01给锁了
 * 等事务01结束之后，事务02才会执行。
 */

public class ShiWu02 {
    public static void main(String[] args) {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            con= DButil.connection("ShowHangJiSuo/test");
            con.setAutoCommit(false);//开启事务
            String sql="update emp set sal=sal*1.1 where job=?";
            ps=con.prepareStatement(sql);//预编译
            ps.setString(1,"manager");
            int count = ps.executeUpdate();
            System.out.println(count);
            con.commit();//提交事务，执行成功结束事务

        } catch (SQLException e) {
            if(con!=null){
                try {
                    con.rollback();//回滚事务，执行失败结束事务
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            DButil.close(con,ps,rs);
        }
    }
}
