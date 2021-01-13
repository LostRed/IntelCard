package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.bean.RoleBean;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.service.RoleService;

import java.util.List;

/**
 * 角色请求Controller
 */
@Controller("role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色
     *
     * @return 应答对象
     */
    @ReqMethod("findAll")
    public RespBean findAll() {
        List<RoleBean> roleList = roleService.findAllBean();
        return new RespBean(0, null, null, roleList);
    }
}
