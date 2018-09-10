package cn.tedu.web;

import cn.tedu.factory.BasicFactory;
import cn.tedu.utils.WebUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("serial")
public class RegistServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.处理请求参数问题（post）
        request.setCharacterEncoding("utf-8");
        // 处理响应正文乱码
        response.setContentType("text/html;charset=utf-8");

        // 2.校验验证码
        String valistr = request.getParameter("valistr");
        if (WebUtils.isNull(valistr)) {
            // 将提示存入request域中，通过转发将消息带到regist.jsp中
            request.setAttribute("msg", "验证码不能为空！");
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }
        // >>验证码是否正确
        // 从session中获取验证码内容
        String code = (String) request.getSession().getAttribute("code");
        if (!valistr.equalsIgnoreCase(code)) {
            // 将提示存入request域中，通过转发将消息带到regist.jsp中
            request.setAttribute("msg", "验证码不正确！");
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }

        // 3.获取请求参数,并将数据封装到JavaBean中
        cn.tedu.domain.User user = new cn.tedu.domain.User();
        // 可以用BeanUtils包来封装数据，比较快捷，需要先导入jar包，在引入包的时候，注意要使用org.apache开头的包
        try {
            BeanUtils.populate(user, request.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        // 4.调用JavaBean的checkData方法校验数据
        try {
            user.checkData();
            // 5.实现注册(将用户信息保存进数据库)
            cn.tedu.service.UserService service = BasicFactory.getFactory().getInstance(cn.tedu.service.UserService.class);
            service.registUser(user);
        } catch (cn.tedu.exception.MsgException e) {
            // 将MsgException中的异常信息存入request域中
            request.setAttribute("msg", e.getMessage());
            // 跳转回注册页面, 显示提示消息
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
            return;
        }

        // 6.提示用户注册成功，3秒后跳转到首页。
        response.getWriter().write("<h1 style='color:red;text-align:center'>恭喜您注册成功，3秒之后跳转回首页...</h1>");
        response.setHeader("refresh", "3;url=" + request.getContextPath() + "/index.jsp");

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
