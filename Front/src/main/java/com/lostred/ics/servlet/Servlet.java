package com.lostred.ics.servlet;

import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.util.WiredUtil;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 服务器主业务Servlet
 */
public class Servlet extends HttpServlet {
    /**
     * 全局会话对象
     */
    public static HttpSession SESSION;
    /**
     * 远程访问地址
     */
    public static String REMOTE_ADDR;

    @Override
    public void init() {
        try {
            WiredUtil.scanClass("com.lostred.ics");
            WiredUtil.wiredAll();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");
        RespBean respBean = new RespBean();
        //解析url地址
        REMOTE_ADDR = req.getRemoteAddr();
        SESSION = req.getSession();
        String uri = req.getRequestURI();
        //获取请求的controller类名
        String className = uri.substring(0, uri.indexOf(".do"));
        className = className.substring(className.lastIndexOf("/") + 1);
        //获取请求的controller类的方法名
        String methodName = req.getParameter("method");
        //获取该类
        Class<?> clazz = WiredUtil.CONTROLLER_CLASS_MAP.get(className);
        if (clazz == null) {
            respBean.setState(-1);
            respBean.setMsgInfo("没有该服务！");
            output(respBean, resp.getWriter());
            return;
        }
        //获取该方法
        Method method = getMethod(clazz, methodName);
        //如果没有找到该方法
        if (method == null) {
            respBean.setState(-1);
            respBean.setMsgInfo("没有该服务！");
            output(respBean, resp.getWriter());
            return;
        }
        Object controller = WiredUtil.CONTROLLER_INSTANCE_MAP.get(className);
        //构造参数集合
        Collection<Object> paramValues = getParamValues(method, req.getParameterMap());
        try {
            //反射调用该方法
            respBean = (RespBean) method.invoke(controller, paramValues.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        output(respBean, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }

    private Method getMethod(Class<?> clazz, String methodName) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getAnnotation(ReqMethod.class).value().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    private Collection<Object> getParamValues(Method method, Map<String, String[]> reqParams) {
        Collection<Object> paramValues = new ArrayList<>();
        //遍历方法的参数数组
        for (Parameter parameter : method.getParameters()) {
            boolean flag = false;
            //遍历请求的参数数组
            for (String paramKey : reqParams.keySet()) {
                if (parameter.getAnnotation(ReqParam.class).value().equals(paramKey)) {
                    flag = true;
                    paramValues.add(reqParams.get(paramKey)[0]);
                    break;
                }
            }
            //如果请求的参数数组没有对应的参数
            if (!flag) {
                paramValues.add(null);
            }
        }
        return paramValues;
    }

    private void output(RespBean respBean, PrintWriter printWriter) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(respBean);
        printWriter.print(jsonString);
        printWriter.flush();
        printWriter.close();
    }
}
