package com.lostred.ics.service;

import com.lostred.ics.bean.ApplyBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;

import java.util.List;

/**
 * 一卡通申请业务接口
 */
public interface ApplyService extends BaseService<ApplyBean> {
    /**
     * 审批一卡通
     *
     * @param applyBean     申请对象
     * @param cardCodeStart 一卡通开始卡号
     * @param actionUser    执行用户
     * @return 受影响的数据数量
     */
    int auditApply(ApplyBean applyBean, String cardCodeStart, UserBean actionUser);

    /**
     * 申请领取一卡通
     *
     * @param applyNum   申请卡数量
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int insertApply(int applyNum, UserBean actionUser);

    /**
     * 根据条件分页查询一卡通申请
     *
     * @param queryBeans 查询对象
     * @param pageBean   分页对象
     * @param field      排序字段
     * @param desc       是否降序排序
     * @return 卡通申请对象集合
     */
    List<ApplyBean> fastFindBeanByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc);
}
