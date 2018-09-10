package cn.tedu.dao;

public interface UserDao {
    boolean findUserByUsername(String username);

    void addUser(cn.tedu.domain.User user);

    cn.tedu.domain.User findUserByUsernameAndPassword(String username, String password);
}
