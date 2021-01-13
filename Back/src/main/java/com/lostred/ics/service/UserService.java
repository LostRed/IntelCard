package com.lostred.ics.service;

import com.lostred.ics.bean.UserBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;

import java.util.List;

/**
 * 用户业务接口
 */
public interface UserService extends BaseService<UserBean> {
    /**
     * 登录校验
     *
     * @param username   用户名
     * @param password   密码
     * @param remoteAddr 远程地址
     * @return 登录成功返回该用户，否则返回null
     */
    UserBean loginCheck(String username, String password, String remoteAddr);

    /**
     * 更改用户的颚状态
     *
     * @param paramId    参数id
     * @param userIds    用户id数组
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int changeState(int paramId, int[] userIds, UserBean actionUser);

    /**
     * 重置用户密码
     *
     * @param userId     用户id
     * @param actionUser 执行用户
     * @return 新密码
     */
    String resetPassword(int userId, UserBean actionUser);

    /**
     * 插入新用户
     *
     * @param userBean   用户对象
     * @param actionUser 执行用户
     * @return 新用户的密码
     */
    String insert(UserBean userBean, UserBean actionUser);

    /**
     * 根据角色id查找用户
     *
     * @param roleId 角色id
     * @return 用户对象集合
     */
    List<UserBean> findByRoleId(int roleId);

    /**
     * 修改密码
     *
     * @param oldPwd     旧密码
     * @param newPwd     新密码
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int modifyPwd(String oldPwd, String newPwd, UserBean actionUser);

    /**
     * 查询所有工作人员
     *
     * @return 用户对象集合
     */
    List<UserBean> findWorkUser();

    /**
     * 根据条件分页查询用户
     *
     * @param queryBeans 查询对象
     * @param pageBean   分页对象
     * @param field      排序字段
     * @param desc       是否降序排序
     * @return 用户对象集合
     */
    List<UserBean> fastFindBeanByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc);
}
