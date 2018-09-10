package cn.tedu.utils;

public class WebUtils {
    //构造方法私有化，防止别人创建示例
    private WebUtils() {
    }

    /**
     * 检查字符串是否为空字符串或者null
     *
     * @param str String
     * @return boolean true表示空字符串或者null
     */
    public static boolean isNull(String str) {
        return str == null || "".equals(str.trim());
    }

}
