package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.bean.ParamBean;
import com.lostred.ics.bean.RoleBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.service.LogService;
import com.lostred.ics.service.UserService;
import com.lostred.ics.servlet.Servlet;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * 用户请求Controller
 */
@Controller("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;

    /**
     * 登录
     *
     * @param username   用户名
     * @param password   密码
     * @param verifyCode 验证码
     * @return 应答对象
     */
    @ReqMethod("login")
    public RespBean login(@ReqParam("username") String username,
                          @ReqParam("password") String password,
                          @ReqParam("verifyCode") String verifyCode) {
        RespBean respBean = new RespBean();
        String code = (String) Servlet.SESSION.getAttribute("code");
        if (!code.equalsIgnoreCase(verifyCode)) {
            respBean.setState(-2);
            respBean.setMsgInfo("验证码错误！");
            return respBean;
        }
        UserBean userBean = userService.loginCheck(username, password, Servlet.REMOTE_ADDR);
        if (userBean != null) {
            if (userBean.getState() == null) {
                respBean.setState(-1);
                respBean.setMsgInfo("服务器异常！");
            } else if ("启用".equals(userBean.getState().getParamName())) {
                respBean.setState(0);
                respBean.setMsgInfo("登录成功！");
                respBean.setData(userBean);
                Servlet.SESSION.setAttribute("loginUser", userBean);
            } else {
                respBean.setState(1);
                respBean.setMsgInfo("该账户已禁用！");
            }
        } else {
            respBean.setState(2);
            respBean.setMsgInfo("用户名或密码错误！");
        }
        return respBean;
    }

    /**
     * 分页查询用户
     *
     * @param currentPageString 当前页json
     * @param pageSizeString    页面行数json
     * @param queryBeansString  查询条件对象json
     * @param field             排序列名
     * @param descString        是否降序
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("findByPage")
    public RespBean findByPage(@ReqParam("currentPage") String currentPageString,
                               @ReqParam("pageSize") String pageSizeString,
                               @ReqParam("queryBeans") String queryBeansString,
                               @ReqParam("field") String field,
                               @ReqParam("desc") String descString) throws IOException {
        PageBean pageBean = PageBean.createPageBean(currentPageString, pageSizeString);
        ObjectMapper mapper = new ObjectMapper();
        QueryBean[] queryBeans = mapper.readValue(queryBeansString, QueryBean[].class);
        boolean desc = mapper.readValue(descString, boolean.class);
        int count = userService.countFindBean(queryBeans);
        pageBean.setCount(count);
        List<UserBean> userList = userService.fastFindBeanByPage(queryBeans, pageBean, "NLSSORT(" + field + ",'NLS_SORT=SCHINESE_PINYIN_M')", desc);
        return new RespBean(0, null, pageBean, userList);
    }

    /**
     * 更改用户状态
     *
     * @param paramIdString 参数json
     * @param userIdsString 用户id数组json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("changeUser")
    public RespBean changeUser(@ReqParam("paramId") String paramIdString, @ReqParam("userIds") String userIdsString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        int[] userIds = mapper.readValue(userIdsString, int[].class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = userService.changeState(Integer.parseInt(paramIdString), userIds, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 重置用户密码
     *
     * @param userIdString 用户id json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("resetUser")
    public RespBean resetUser(@ReqParam("userId") String userIdString) throws IOException {
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        RespBean respBean;
        if (userIdString != null && !"".equals(userIdString)) {
            int userId = Integer.parseInt(userIdString);
            String password = userService.resetPassword(userId, actionUser);
            if (password != null) {
                respBean = new RespBean(0, "success", null, password);
            } else {
                respBean = new RespBean(1, "fail", null, null);
            }
        } else {
            respBean = new RespBean(1, "fail", null, null);
        }
        return respBean;
    }

    /**
     * 新增用户
     *
     * @param nameString      姓名json
     * @param passwordString  密码json
     * @param paramBeanString 科室参数json
     * @param roleBeanString  角色json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("insertUser")
    public RespBean insertUser(@ReqParam("name") String nameString,
                               @ReqParam("password") String passwordString,
                               @ReqParam("section") String paramBeanString,
                               @ReqParam("roleBean") String roleBeanString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ParamBean section = mapper.readValue(paramBeanString, ParamBean.class);
        RoleBean roleBean = mapper.readValue(roleBeanString, RoleBean.class);
        UserBean userBean = new UserBean();
        userBean.setName(nameString);
        userBean.setPassword(passwordString);
        userBean.setSection(section);
        userBean.setRoleBean(roleBean);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        String username = userService.insert(userBean, actionUser);
        RespBean respBean;
        if (username != null) {
            respBean = new RespBean(0, "success", null, username);
        } else {
            respBean = new RespBean(1, "fail", null, null);
        }
        return respBean;
    }

    /**
     * 修改用户
     *
     * @param userString 用户json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("updateUser")
    public RespBean updateUser(@ReqParam("userBean") String userString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserBean userBean = mapper.readValue(userString, UserBean.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = userService.updateBean(userBean, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 退出
     *
     * @return 应答对象
     */
    @ReqMethod("exit")
    public RespBean exit() {
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = logService.logoff(actionUser);
        RespBean respBean;
        if (num > 0) {
            Servlet.SESSION.setAttribute("loginUser", null);
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 根据角色id查询用户
     *
     * @param roleIdString 角色id json
     * @return 应答对象
     */
    @ReqMethod("findByRoleId")
    public RespBean findByRoleId(@ReqParam("roleId") String roleIdString) {
        int roleId = Integer.parseInt(roleIdString);
        List<UserBean> userList = userService.findByRoleId(roleId);
        return new RespBean(0, null, null, userList);
    }

    /**
     * 修改密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return 应答对象
     */
    @ReqMethod("modifyPwd")
    public RespBean modifyPwd(@ReqParam("oldPwd") String oldPwd,
                              @ReqParam("newPwd") String newPwd) {
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = userService.modifyPwd(oldPwd, newPwd, actionUser);
        RespBean respBean;
        if (num > 0) {
            Servlet.SESSION.setAttribute("loginUser", null);
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 查找所有工作人员(管理员除外)
     *
     * @return 应答对象
     */
    @ReqMethod("findWorkUser")
    public RespBean findWorkUser() {
        List<UserBean> userList = userService.findWorkUser();
        return new RespBean(0, null, null, userList);
    }
}
