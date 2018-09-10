package cn.tedu.factory;

import java.io.FileInputStream;
import java.util.Properties;

public class UserDaoFactory {
    private static cn.tedu.factory.UserDaoFactory factory = new cn.tedu.factory.UserDaoFactory();
    private static Properties prop = new Properties();

    static {
        try {
            String path = cn.tedu.factory.UserDaoFactory.class.getClassLoader().getResource("config.properties").getPath();
            prop.load(new FileInputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 私有化构造方法 禁止外界new 工厂
     */
    private UserDaoFactory() {
    }

    /**
     * 将工厂单例 从这个方法获取唯一的工厂
     *
     * @return
     */
    public static cn.tedu.factory.UserDaoFactory getFactory() {
        return factory;
    }

    /**
     * 通过工厂 根据配置文件配置 获取UserDao的实现
     *
     * @return
     * @throws Exception
     */
    public cn.tedu.dao.UserDao getInstance() {
        try {
            String userDaoStr = prop.getProperty("UserDao");
            Class<?> clz = Class.forName(userDaoStr);
            return (cn.tedu.dao.UserDao) clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
