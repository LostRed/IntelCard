package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.bean.MenuBean;
import com.lostred.ics.bean.RoleBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.service.PermService;
import com.lostred.ics.servlet.Servlet;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * 权限请求Controller
 */
@Controller("perm")
public class PermController {
    @Autowired
    private PermService permService;

    /**
     * 给一个角色新增一个菜单权限
     *
     * @param roleString    角色json
     * @param addMenuString 菜单json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("insertPerm")
    public RespBean insertPerm(@ReqParam("role") String roleString,
                               @ReqParam("addMenu") String addMenuString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RoleBean roleBean = mapper.readValue(roleString, RoleBean.class);
        MenuBean menuBean = mapper.readValue(addMenuString, MenuBean.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = permService.insertPerm(roleBean, menuBean, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 给一个角色新增所有菜单权限
     *
     * @param roleString           角色json
     * @param hasNotMenuListString 未拥有的菜单集合json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("insertAll")
    public RespBean insertAll(@ReqParam("role") String roleString,
                              @ReqParam("hasNotMenuList") String hasNotMenuListString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RoleBean roleBean = mapper.readValue(roleString, RoleBean.class);
        MenuBean[] menuBeans = mapper.readValue(hasNotMenuListString, MenuBean[].class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = permService.insertAllPerm(roleBean, menuBeans, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 给一个角色新移除一个菜单权限
     *
     * @param roleString       角色json
     * @param removeMenuString 菜单json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("removePerm")
    public RespBean removePerm(@ReqParam("role") String roleString,
                               @ReqParam("removeMenu") String removeMenuString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RoleBean roleBean = mapper.readValue(roleString, RoleBean.class);
        MenuBean menuBean = mapper.readValue(removeMenuString, MenuBean.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = permService.removePerm(roleBean, menuBean, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 给一个角色新移除所有菜单权限
     *
     * @param roleString 角色json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("removeAll")
    public RespBean removeAll(@ReqParam("role") String roleString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RoleBean roleBean = mapper.readValue(roleString, RoleBean.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = permService.removeAllPerm(roleBean, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }
}
