package cn.tedu.dao;

import cn.tedu.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements cn.tedu.dao.UserDao {
    /**
     * 根据用户名查询用户是否存在
     *
     * @param username 用户名
     * @return true表示用户名存在，false表示不存在
     */
    public boolean findUserByUsername(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from user where username=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            return rs.next(); // true表示存在
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
    }

    /**
     * 将用户注册信息保存进数据库中
     *
     * @param user User对象
     */
    public void addUser(cn.tedu.domain.User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into user values(null,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNickname());
            ps.setString(4, user.getEmail());

            // 执行sql
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
    }

    /**
     * 根据用户名密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return User对象
     */
    public cn.tedu.domain.User findUserByUsernameAndPassword(String username, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from user where username=? and password=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {// 用户名密码正确
                // 将结果集中的第一条记录封装到JavaBean并返回
                cn.tedu.domain.User user = new cn.tedu.domain.User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setEmail(rs.getString("email"));

                return user;
            } else {// 用户名或密码错误
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            JDBCUtils.close(conn, ps, rs);
        }
    }
}
