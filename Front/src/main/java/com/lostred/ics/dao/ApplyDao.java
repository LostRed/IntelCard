package com.lostred.ics.dao;

import com.lostred.ics.bean.ApplyBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 领卡申请dao接口
 */
public interface ApplyDao extends BaseDao<ApplyBean> {
    /**
     * 根据用户id查询领卡申请id集合
     *
     * @param conn        数据库连接对象
     * @param ApplyUserId 领卡申请id
     * @return 领卡申请id数组
     * @throws SQLException SQL异常
     */
    List<Integer> findIdByApplyUserId(Connection conn, int ApplyUserId) throws SQLException;

    /**
     * 根据条件分页查询一卡通申请
     *
     * @param queryBeans 查询对象
     * @param pageBean   分页对象
     * @param field      排序字段
     * @param desc       是否降序排序
     * @return 一卡通申请对象集合
     * @throws SQLException SQL异常
     */
    List<ApplyBean> fastFindBeanByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException;
}
