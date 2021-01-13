package com.lostred.ics.service;

import com.lostred.ics.bean.MenuBean;

import java.util.List;

/**
 * 菜单业务接口
 */
public interface MenuService extends BaseService<MenuBean> {
    /**
     * 更具用户id查询该用户拥有的菜单
     *
     * @param userId 用户id
     * @return 菜单集合
     */
    List<MenuBean> findByUserId(int userId);

    /**
     * 更具角色id查询该角色拥有的菜单
     *
     * @param RoleId 角色id
     * @return 菜单集合
     */
    List<MenuBean> findHasByRoleId(int RoleId);

    /**
     * 更具角色id查询该角色未拥有的菜单
     *
     * @param RoleId 角色id
     * @return 菜单集合
     */
    List<MenuBean> findHasNotByRoleId(int RoleId);
}
