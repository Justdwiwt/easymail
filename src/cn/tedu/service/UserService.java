package cn.tedu.service;

public interface UserService {
    void registUser(cn.tedu.domain.User user) throws cn.tedu.exception.MsgException;

    cn.tedu.domain.User loginUser(String username, String password);

    boolean hasUser(String username);
}
