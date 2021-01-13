package com.lostred.ics.service;

import com.lostred.ics.bean.UserBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;

import java.util.List;

/**
 * 基础业务接口
 *
 * @param <T> 数据库实体类
 */
public interface BaseService<T> {
    /**
     * 新增实体
     *
     * @param bean     实体对象
     * @param userBean 执行用户
     * @return 受影响的数据数量
     */
    int insertBean(T bean, UserBean userBean);

    /**
     * 删除实体
     *
     * @param id       实体主键id
     * @param userBean 执行用户
     * @return 受影响的数据数量
     */
    int deleteBeanById(int id, UserBean userBean);

    /**
     * 批量删除实体
     *
     * @param ids      实体主键id数组
     * @param userBean 执行用户
     * @return 受影响的数据数量
     */
    int batchDeleteBean(int[] ids, UserBean userBean);

    /**
     * 修改实体
     *
     * @param bean     实体对象
     * @param userBean 执行用户
     * @return 受影响的数据数量
     */
    int updateBean(T bean, UserBean userBean);

    /**
     * 根据实体主键id查找单个实体
     *
     * @param id 实体主键id数组
     * @return 受影响的数据数量
     */
    T findBeanById(int id);

    /**
     * 查询一系列数据持久化实体的数据总数
     *
     * @param queryBeans 查询实体
     * @return 数据持久化实体集合
     */
    int countFindBean(QueryBean[] queryBeans);

    /**
     * 查询一系列数据持久化实体
     *
     * @param queryBeans 查询实体
     * @param pageBean   页码实体
     * @param field      排序字段
     * @param desc       是否降序排序
     * @return 数据持久化实体集合
     */
    List<T> findBeanByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc);

    /**
     * 查询所有数据持久化实体
     *
     * @return 数据持久化实体集合
     */
    List<T> findAllBean();
}
