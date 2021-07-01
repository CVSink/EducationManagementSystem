package service.impl;

import service.LoginService;
import bean.Login;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServiceImpl implements LoginService {
    @Override
    public Login getUser(int ID, int password){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            String sql = "select * from adminUser where ID = ? and password = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, ID);
            ps.setInt(2,password);
            // 4.执行语句
            rs = ps.executeQuery();
            if (rs.next()) {
                Login login = new Login(rs.getString("ID"),rs.getString("password"));
                return login;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5.释放资源
            JdbcUtil.close(conn, ps, rs);
        }
        return null;
    }

}