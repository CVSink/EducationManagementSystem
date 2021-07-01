package service.impl;

import service.TeacherService;
import bean.Teacher;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    public int executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            ps = conn.prepareStatement(sql);
            // 遍历参数
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            // 4.执行语句
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5.释放资源
            JdbcUtil.close(conn, ps, null);
        }
        return 0;
    }

    @Override
    public void save(Teacher tea) {
        String sql = "insert into Teacher(teaID,teaName,teaSex,teaBirth,teaMajor) values (?,?,?,?,?)";
        this.executeUpdate(sql, tea.getTeaID(), tea.getTeaName(), tea.getTeaSex(), tea.getTeaBirth(),tea.getTeaMajor());
    }

    @Override
    public void delete(int tId) {
        String sql = "delete from teacher where teaID = ?";
        this.executeUpdate(sql, tId);
    }

    @Override
    public void update(int tId, Teacher tea) {
        String sql = "update teacher set teaName=?, teaSex=?,teaBirth=?,teaMajor=? where teaId =? ";
        this.executeUpdate(sql, tea.getTeaName(), tea.getTeaSex(),
                tea.getTeaBirth(), tea.getTeaMajor(), tId);
    }

    @Override
    public Teacher get(int tId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            String sql = "select * from Teacher where teaID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, tId);
            // 4.执行语句
            rs = ps.executeQuery();
            if (rs.next()) {
                Teacher tea = new Teacher(rs.getString("teaID"),
                        rs.getString("teaName"),rs.getString("teaSex"),rs.getString("teaBirth"),
                        rs.getString("teaMajor"));
                return tea;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 5.释放资源
            JdbcUtil.close(conn, ps, rs);
        }
        return null;
    }

    @Override
    public List<Teacher> getAll() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConn();
            // 3.创建语句
            st = conn.createStatement();
            String sql = "select * from teacher ";
            System.out.println(sql);
            // 4.执行语句
            rs = st.executeQuery(sql);
            // 创建一个集合
            List<Teacher> list = new ArrayList<>();
            while (rs.next()) {
                Teacher tea = new Teacher(
                        rs.getString("teaID"),
                        rs.getString("teaName"),
                        rs.getString("teaSex"),
                        rs.getString("teaBirth"),
                        rs.getString("teaMajor"));
                list.add(tea);
            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn, st, rs);
        }
        return null;
    }
}
