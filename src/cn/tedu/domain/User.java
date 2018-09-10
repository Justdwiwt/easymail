package cn.tedu.domain;

import cn.tedu.utils.WebUtils;

public class User {
    private int id;
    private String username;
    private String password;
    private String password2;
    private String nickname;
    private String email;

    public User() {
    }

    public User(int id, String username, String password, String nickname, String email) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public void checkData() throws cn.tedu.exception.MsgException {
        // >>非空校验
        if (WebUtils.isNull(username)) {
            throw new cn.tedu.exception.MsgException("用户名不能为空！");
        }
        if (WebUtils.isNull(password)) {
            throw new cn.tedu.exception.MsgException("密码不能为空！");
        }
        if (WebUtils.isNull(password2)) {
            throw new cn.tedu.exception.MsgException("确认密码不能为空！");
        }
        if (WebUtils.isNull(nickname)) {
            throw new cn.tedu.exception.MsgException("昵称不能为空！");
        }
        if (WebUtils.isNull(email)) {
            throw new cn.tedu.exception.MsgException("邮箱不能为空！");
        }

        // >>两次密码是否一致
        if (!password.equals(password2)) {
            throw new cn.tedu.exception.MsgException("两次密码不一致！");
        }

        // >>邮箱格式校验
        // abc@sina.com.cn
        if (!email.matches("^\\w+@\\w+(\\.\\w+)+$")) {
            throw new cn.tedu.exception.MsgException("邮箱格式不正确！");
        }
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
