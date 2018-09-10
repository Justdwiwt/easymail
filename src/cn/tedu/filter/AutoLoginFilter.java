package cn.tedu.filter;

import cn.tedu.factory.BasicFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

public class AutoLoginFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        cn.tedu.service.UserService service = BasicFactory.getFactory().getInstance(cn.tedu.service.UserService.class);
        if (req.getSession(false) == null || req.getSession().getAttribute("user") == null) {
            Cookie[] cs = req.getCookies();
            Cookie findC = null;
            if (cs != null)
                for (Cookie c : cs)
                    if ("autologin".equals(c.getName())) {
                        findC = c;
                        break;
                    }
            if (findC != null) {
                String value = URLDecoder.decode(findC.getValue(), "utf-8");
                String username = value.split(":")[0];
                String MD5Psw = value.split(":")[1];
                cn.tedu.domain.User user = service.loginUser(username, MD5Psw);
                if (user != null)
                    req.getSession().setAttribute("user", user);
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

}
