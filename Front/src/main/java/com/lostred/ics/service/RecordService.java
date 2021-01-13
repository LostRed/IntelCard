package com.lostred.ics.service;

import com.lostred.ics.bean.RecordBean;
import com.lostred.ics.query.PageBean;

import java.util.List;

/**
 * 一卡通消费记录业务接口
 */
public interface RecordService extends BaseService<RecordBean> {
    /**
     * 根据一卡通id查询消费记录的总数
     *
     * @param cardId 一卡通id
     * @return 记录总数
     */
    int countFindByCardId(int cardId);

    /**
     * 根据一卡通id分页查询消费记录
     *
     * @param cardId   一卡通id
     * @param pageBean 分页对象
     * @return 消费记录对象集合
     */
    List<RecordBean> findByPageAndCardId(int cardId, PageBean pageBean);
}
