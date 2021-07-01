package util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.sql.DataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class JdbcUtil {

    public static DataSource ds = null;

    static {
        try {
            //1.加载配置文件
            Properties p = new Properties();
            File directory = new File("");//设定为当前文件夹

                System.out.println(directory.getCanonicalPath());//获取标准的路径
                System.out.println(directory.getAbsolutePath());//获取绝对路径

            FileInputStream in = new FileInputStream("./src/main/resources/db.properties");
            p.load(in);
            //ds = BasicDataSourceFactory.createDataSource(p);
            ds = DruidDataSourceFactory.createDataSource(p);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConn() {
        try {
            // 2.连接数据
            return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 关闭资源
     */
    public static void close(Connection conn,Statement st,ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}