package com.lostred.ics.service;

import com.lostred.ics.bean.MenuBean;
import com.lostred.ics.bean.RoleBean;
import com.lostred.ics.bean.UserBean;

/**
 * 权限业务接口
 */
public interface PermService {
    /**
     * 给角色增加一个新菜单
     *
     * @param roleBean   角色对象
     * @param menuBean   菜单对象
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int insertPerm(RoleBean roleBean, MenuBean menuBean, UserBean actionUser);

    /**
     * 给角色增加所有菜单
     *
     * @param roleBean   角色对象
     * @param menuBeans  菜单对象数组
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int insertAllPerm(RoleBean roleBean, MenuBean[] menuBeans, UserBean actionUser);

    /**
     * 给角色移除一个菜单
     *
     * @param roleBean   角色对象
     * @param menuBean   菜单对象
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int removePerm(RoleBean roleBean, MenuBean menuBean, UserBean actionUser);

    /**
     * 给角色移除所有菜单
     *
     * @param roleBean   角色对象
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int removeAllPerm(RoleBean roleBean, UserBean actionUser);
}
