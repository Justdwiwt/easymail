package cn.tedu.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtils {
    private static ComboPooledDataSource pool = new ComboPooledDataSource();

    private JDBCUtils() {
    }

    /**
     * 获取连接池的方法
     *
     * @return DataSource
     */
    @SuppressWarnings("unused")
    public static DataSource getPool() {
        return pool;
    }

    /**
     * 从连接池中获取一个连接
     *
     * @return Connection
     */
    public static Connection getConnection() {
        try {
            return pool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 释放资源
     *
     * @param conn 连接对象
     * @param stat 传输器对象
     * @param rs   结果集对象
     */
    @SuppressWarnings({"Duplicates", "UnusedAssignment"})
    public static void close(Connection conn, Statement stat, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                rs = null;
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stat = null;
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn = null;
            }
        }
    }

}
