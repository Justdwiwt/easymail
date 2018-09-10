package cn.tedu.service;

import cn.tedu.factory.BasicFactory;

public class UserServiceImpl implements cn.tedu.service.UserService {
    // private UserDaoImpl dao = new UserDaoImpl();
    private cn.tedu.dao.UserDao dao = BasicFactory.getFactory().getInstance(cn.tedu.dao.UserDao.class);

    /**
     * 实现注册
     *
     * @param user
     * @throws cn.tedu.exception.MsgException
     */
    public void registUser(cn.tedu.domain.User user) throws cn.tedu.exception.MsgException {
        // 1.检查用户名是否存在
        boolean result = dao.findUserByUsername(user.getUsername());
        if (result) {// 用户名已存在
            throw new cn.tedu.exception.MsgException("用户名已存在");
        }
        // 2.实现注册（保存数据到数据库）
        dao.addUser(user);
    }

    /**
     * 实现登录
     *
     * @param username 用户名
     * @param password 密码
     * @return User对象
     */
    public cn.tedu.domain.User loginUser(String username, String password) {
        return dao.findUserByUsernameAndPassword(username, password);
    }

    /**
     * 根据用户名查询用户是否存在
     *
     * @param username 用户名
     * @return true表示用户名已存在，false表示不存在
     */
    public boolean hasUser(String username) {
        return dao.findUserByUsername(username);
    }
}
