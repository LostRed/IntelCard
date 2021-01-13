package com.lostred.ics.servlet;

import com.lostred.ics.util.ImageCodeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 服务器验证码Servlet
 */
public class CodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ImageCodeUtil icu = new ImageCodeUtil();     //用我们的验证码类，生成验证码类对象
        BufferedImage image = icu.getImage();  //获取验证码
        req.getSession().setAttribute("code", icu.getText()); //将验证码的文本存在session中
        icu.output(image, resp.getOutputStream());//将验证码图片响应给客户端
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
