package com.lostred.ics.service.impl;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Service;
import com.lostred.ics.bean.MenuBean;
import com.lostred.ics.dao.MenuDao;
import com.lostred.ics.service.MenuService;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service("MenuService")
public class MenuServiceImpl extends BaseServiceImpl<MenuBean> implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<MenuBean> findByUserId(int userId) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<MenuBean> list = new ArrayList<>();
        try {
            list = menuDao.findByUserId(conn, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public List<MenuBean> findHasByRoleId(int RoleId) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<MenuBean> parentMenus = new ArrayList<>();
        try {
            List<MenuBean> list = menuDao.findHasByRoleId(conn, RoleId);
            for (MenuBean menuBean : list) {
                boolean flag = false;
                for (MenuBean parentMenu : parentMenus) {
                    if (parentMenu.getMenuId() == menuBean.getParentMenuBean().getMenuId()) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    parentMenus.add(menuBean.getParentMenuBean());
                }
            }
            parentMenus.addAll(list);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return parentMenus;
    }

    @Override
    public List<MenuBean> findHasNotByRoleId(int RoleId) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<MenuBean> parentMenus = new ArrayList<>();
        try {
            List<MenuBean> list = menuDao.findHasNotByRoleId(conn, RoleId);
            for (MenuBean menuBean : list) {
                boolean flag = false;
                for (MenuBean parentMenu : parentMenus) {
                    if (parentMenu.getMenuId() == menuBean.getParentMenuBean().getMenuId()) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    parentMenus.add(menuBean.getParentMenuBean());
                }
            }
            parentMenus.addAll(list);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return parentMenus;
    }
}
