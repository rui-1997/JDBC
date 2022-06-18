package ShowHangJiSuo;

import util.DButil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShiWu01 {
    public static void main(String[] args) {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            con= DButil.connection("ShowHangJiSuo/test");
            con.setAutoCommit(false);//开启事务
            String sql="select empno,ename,sal from emp where job=? for update ";//被for update锁住的数据，只有在该事务执行结束之后，
                                                                                 // 其他事务才能对被锁住的数据进行修改
            ps=con.prepareStatement(sql);//预编译
            ps.setString(1,"manager");
            rs=ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getInt("empno")+","+rs.getString("ename")+","+rs.getDouble("sal"));
            }
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
