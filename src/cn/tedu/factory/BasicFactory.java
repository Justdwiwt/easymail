package cn.tedu.factory;

import java.io.FileInputStream;
import java.util.Properties;

public class BasicFactory {
    private static BasicFactory factory = new BasicFactory();
    private static Properties prop = new Properties();

    static {
        try {
            String path = BasicFactory.class.getClassLoader().getResource("conf.properties").getPath();
            prop.load(new FileInputStream(path));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 私有化构造方法 禁止外界new 工厂
     */
    private BasicFactory() {
    }

    /**
     * 将工厂单例 从这个方法获取唯一的工厂
     *
     * @return
     */
    public static BasicFactory getFactory() {
        return factory;
    }

    /**
     * 通过工厂 根据配置文件配置 获取UserDao的实现
     *
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> T getInstance(Class<T> clazz) {
        try {
            String className = prop.getProperty(clazz.getSimpleName());
            Class clz = Class.forName(className);
            return (T) clz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
