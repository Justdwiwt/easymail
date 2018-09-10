package cn.tedu.backend.web;

import cn.tedu.factory.BasicFactory;
import cn.tedu.service.ProdService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManageDelProdServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // 获取请求参数-》pid
        Integer pid = Integer.parseInt(req.getParameter("pid"));
        // 调用service执行删除商品的逻辑
        ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
        boolean flag = service.deleteProd(pid);
        if (flag) {
            resp.getWriter().write("商品删除成功");
        } else {
            resp.getWriter().write("商品删除失败");
        }
        // 定时刷新到修改商品的页面
        resp.setHeader("refresh", "3;url=" + req.getContextPath() + "/ManageProdListServlet");

    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }

}
