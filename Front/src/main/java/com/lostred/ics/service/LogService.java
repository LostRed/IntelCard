package com.lostred.ics.service;

import com.lostred.ics.bean.LogBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dto.StatisticsBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;

import java.util.List;

/**
 * 日志业务接口
 */
public interface LogService extends BaseService<LogBean> {
    /**
     * 退出
     *
     * @param userBean 用户对象
     * @return 受影响的数据数量
     */
    int logoff(UserBean userBean);

    /**
     * 根据条件查询统计数据的总数
     *
     * @param queryBeans 查询条件
     * @return 统计数据总数
     */
    int countFindStatistics(QueryBean[] queryBeans);

    /**
     * 根据条件查询统计数据
     *
     * @param queryBeans 查询条件
     * @param pageBean   分页对象
     * @param field      排序字段
     * @param desc       是否降序
     * @return 统计数据集合
     */
    List<StatisticsBean> findStatisticsByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc);


    /**
     * 快速分页查找日志
     *
     * @param queryBeans 查询对象数组
     * @param pageBean   分页对象
     * @param field      排序字段
     * @param desc       是否降序
     * @return 日志集合
     */
    List<LogBean> fastFindByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc);
}
