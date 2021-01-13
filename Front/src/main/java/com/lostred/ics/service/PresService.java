package com.lostred.ics.service;

import com.lostred.ics.bean.CardBean;
import com.lostred.ics.bean.PresBean;
import com.lostred.ics.bean.UserBean;

/**
 * 处方业务接口
 */
public interface PresService extends BaseService<PresBean> {
    /**
     * 插入新处方
     *
     * @param presBean   处方对象
     * @param actionUser 执行用户
     * @return 受影响的记录数量
     */
    int insert(PresBean presBean, UserBean actionUser);

    /**
     * 处方退费
     *
     * @param cardBean   一卡通对象
     * @param presBeans  处方对象数组
     * @param actionUser 执行用户
     * @return 受影响的记录数量
     */
    int refund(CardBean cardBean, PresBean[] presBeans, UserBean actionUser);
}
