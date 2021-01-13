package com.lostred.ics.service;

import com.lostred.ics.bean.ParamBean;
import com.lostred.ics.bean.UserBean;

import java.util.List;

/**
 * 参数业务接口
 */
public interface ParamService extends BaseService<ParamBean> {
    /**
     * 根据参数类型查询参数实体
     *
     * @param type 参数类型
     * @return 参数对象集合
     */
    List<ParamBean> findByType(String type);

    /**
     * 根据参数值查询一卡通状态
     *
     * @param value 参数值
     * @return 参数对象集合
     */
    List<ParamBean> findCardState(int value);

    /**
     * 根据参数值查询用户状态
     *
     * @param value 参数值
     * @return 参数对象集合
     */
    List<ParamBean> findUserState(int value);

    /**
     * 查询所有参数类型
     *
     * @return 参数类型集合
     */
    List<String> findAllParamType();

    /**
     * 将参数的状态改为不可用
     *
     * @param paramIds   参数id数组
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int changeParam(int[] paramIds, UserBean actionUser);
}
