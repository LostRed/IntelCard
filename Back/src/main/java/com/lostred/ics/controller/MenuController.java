package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.bean.MenuBean;
import com.lostred.ics.bean.RoleBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.service.MenuService;
import com.lostred.ics.servlet.Servlet;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * 菜单请求Controller
 */
@Controller("menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 按照登录用户id查询该用户权限内的菜单
     *
     * @return 应答对象
     */
    @ReqMethod("findByUserId")
    public RespBean findByUserId() {
        UserBean userBean = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        List<MenuBean> menuList = menuService.findByUserId(userBean.getUserId());
        return new RespBean(0, "success", null, menuList);
    }

    /**
     * 根据角色id查询该角色拥有的菜单
     *
     * @param roleString 角色json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("findHasByRoleId")
    public RespBean findHasByRoleId(@ReqParam("role") String roleString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RoleBean roleBean = mapper.readValue(roleString, RoleBean.class);
        List<MenuBean> menuList = menuService.findHasByRoleId(roleBean.getRoleId());
        return new RespBean(0, "success", null, menuList);
    }

    /**
     * 根据角色id查询该角色未拥有的菜单
     *
     * @param roleString 角色json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("findHasNotByRoleId")
    public RespBean findHasNotByRoleId(@ReqParam("role") String roleString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        RoleBean roleBean = mapper.readValue(roleString, RoleBean.class);
        List<MenuBean> menuList = menuService.findHasNotByRoleId(roleBean.getRoleId());
        return new RespBean(0, "success", null, menuList);
    }
}
