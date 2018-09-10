package cn.tedu.web;

import cn.tedu.factory.BasicFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
public class AjaxCheckUsernameServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.处理乱码（load方法为post提交）
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        // 2.获取请求参数（用户名）
        String username = request.getParameter("username");
        // 3.调用service层的方法检查用户名是否存在
        cn.tedu.service.UserService service = BasicFactory.getFactory().getInstance(cn.tedu.service.UserService.class);
        boolean result = service.hasUser(username);
        if (result) {
            response.getWriter().write("用户名已存在！");
        } else {
            response.getWriter().write("恭喜，用户名可以使用！");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
