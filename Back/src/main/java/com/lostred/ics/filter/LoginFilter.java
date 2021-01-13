package com.lostred.ics.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登录过滤器
 */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        // 获得session
        HttpSession session = req.getSession();
        Object loginAdmin = session.getAttribute("loginUser");
        //如果已登录
        if (loginAdmin != null) {
            //执行过滤链
            filterChain.doFilter(req, resp);
        } else {
            //页面重定向
            PrintWriter out = resp.getWriter();
            out.println("<html>");
            out.println("<script>");
            out.println("window.open ('" + req.getContextPath() + "/login.jsp','_top')");
            out.println("</script>");
            out.println("</html>");
        }
    }

    @Override
    public void destroy() {

    }
}
