package cn.tedu.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Map;

public class EncodingFilter implements Filter {

    private FilterConfig config = null;
    private String encode = null;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=" + encode);
        chain.doFilter(new MyHttpServletRequest((HttpServletRequest) request), response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        this.encode = config.getServletContext().getInitParameter("encode");
    }

    public void destroy() {
    }

    class MyHttpServletRequest extends HttpServletRequestWrapper {
        private HttpServletRequest request;
        private boolean hasNotEncode = true;

        public MyHttpServletRequest(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Map<String, String[]> getParameterMap() {
            try {
                if ("POST".equals(request.getMethod())) {
                    request.setCharacterEncoding(encode);
                    return request.getParameterMap();
                } else if ("GET".equals(request.getMethod())) {
                    Map<String, String[]> map = request.getParameterMap();
                    if (hasNotEncode) {
                        for (Map.Entry<String, String[]> entry : map.entrySet()) {
                            String[] vs = entry.getValue();
                            for (int i = 0; i < vs.length; i++)
                                vs[i] = new String(vs[i].getBytes("iso8859-1"), encode);
                        }
                        hasNotEncode = false;
                    }
                    return map;
                } else
                    return request.getParameterMap();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        @Override
        public String[] getParameterValues(String name) {
            return getParameterMap().get(name);
        }

        @Override
        public String getParameter(String name) {
            String[] vs = getParameterValues(name);
            return vs == null ? null : vs[0];
        }
    }
}
