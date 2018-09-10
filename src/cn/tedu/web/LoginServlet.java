package cn.tedu.web;

import cn.tedu.factory.BasicFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.处理请求乱码
        request.setCharacterEncoding("utf-8");
        // 2.获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remname = request.getParameter("remname");
//		String autologin = request.getParameter("autologin");
        // 3.调用service层的方法进行登录
        cn.tedu.service.UserService service = BasicFactory.getFactory().getInstance(cn.tedu.service.UserService.class);
        cn.tedu.domain.User user = service.loginUser(username, password);
        if (user != null) {// 用户名密码正确
            // 4.实现记住用户名功能
            if ("true".equals(remname)) {
                Cookie cookie = new Cookie("remname", URLEncoder.encode(username, "utf-8"));
                cookie.setPath(request.getContextPath() + "/");
                cookie.setMaxAge(60 * 60 * 24 * 30);// 设置30天
                response.addCookie(cookie);
            } else {// 取消记住用户名（删除cookie）
                Cookie cookie = new Cookie("remname", "");
                cookie.setPath(request.getContextPath() + "/");
                cookie.setMaxAge(0);// 设置30天
                response.addCookie(cookie);
            }
            // 5.进行登录
            request.getSession().setAttribute("user", user);

//			// 实现30天自动登录-将用户名和密码保存在cookie中返回给用户
//			if ("true".equals(autologin)) {
//				// 说明勾选了30天自动登录
//				Cookie cookie = new Cookie("autologin", username + ":" + password);
//				cookie.setMaxAge(60 * 60 * 24 * 30);// 30天
//				cookie.setPath(request.getContextPath() + "/");
//				response.addCookie(cookie);
//			}

            // 6.重定向到主页
            response.sendRedirect(request.getContextPath() + "/index.jsp");

        } else {// 用户名密码不正确
            request.setAttribute("msg", "用户名或密码不正确！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
