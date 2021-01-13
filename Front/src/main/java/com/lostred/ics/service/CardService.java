package com.lostred.ics.service;

import com.lostred.ics.bean.CardBean;
import com.lostred.ics.bean.PatientBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;

import java.util.List;

/**
 * 一卡通业务接口
 */
public interface CardService extends BaseService<CardBean> {
    /**
     * 根据一卡通id批量物理删除一卡通
     *
     * @param cardIds    一卡通id数组
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int deleteById(int[] cardIds, UserBean actionUser);

    /**
     * 批量插入一卡通
     *
     * @param prefix        一卡通前缀
     * @param cardCodeStart 一卡通开始卡号
     * @param cardCodeEnd   一卡通截止卡号
     * @param actionUser    执行用户
     * @return 收影响的数据数量
     */
    int batchInsert(String prefix, int cardCodeStart, int cardCodeEnd, UserBean actionUser);

    /**
     * 根据条件查询可注销的一卡通的数据总数
     *
     * @param queryBeans 查询条件数组
     * @return 一卡通总数
     */
    int countCancel(QueryBean[] queryBeans);

    /**
     * 根据条件查询可注销的一卡通
     *
     * @param queryBeans 查询条件数组
     * @param pageBean   分页对象
     * @param field      排序字段
     * @param desc       是否降序
     * @return 一卡通对象集合
     */
    List<CardBean> findCancelByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc);

    /**
     * 根据一卡通id注销一卡通
     *
     * @param cardIds    一卡通id数组
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int cancelById(int[] cardIds, UserBean actionUser);

    /**
     * 根据申请id查询该申请包含的一卡通卡号
     *
     * @param applyId 领卡申请id
     * @return 一卡通卡号集合
     */
    List<String> findByApplyId(int applyId);

    /**
     * 根据一卡通卡号售卡
     *
     * @param patientBean 就诊人对象
     * @param amount      预存金额
     * @param cardCode    一卡通卡号
     * @param actionUser  执行用户
     * @return 受影响的数据数量
     */
    int saleByCardCode(PatientBean patientBean, int amount, String cardCode, UserBean actionUser);

    /**
     * 根据卡号查找该一卡通是否可出售
     *
     * @param cardCode 一卡通卡号
     * @return 该一卡通对象，不可出售返回null
     */
    CardBean findSaleableByCardCode(String cardCode);

    /**
     * 根据卡号或身份证号或手机号查找该一卡通是否已出售
     *
     * @param keyword 搜索关键词
     * @return 该一卡通对象，不是已出售状态返回null
     */
    CardBean findSoldOut(String keyword);

    /**
     * 换卡
     *
     * @param oldCard    旧卡对象
     * @param cardCode   新卡卡号
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int changCard(CardBean oldCard, String cardCode, UserBean actionUser);

    /**
     * 退卡
     *
     * @param oldCard    旧卡对象
     * @param actionUser 执行用户
     * @return 受影响的数据数量
     */
    int refundCard(CardBean oldCard, UserBean actionUser);

    /**
     * 充值
     *
     * @param oldCard  旧卡对象
     * @param recharge 充值金额
     * @return 受影响的数据数量
     */
    int rechargeCard(CardBean oldCard, int recharge);

    /**
     * 快速分页查找一卡通
     *
     * @param queryBeans 查询对象数组
     * @param pageBean   分页对象
     * @param field      排序字段
     * @param desc       是否降序
     * @return 一卡通集合
     */
    List<CardBean> fastFindByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc);

    /**
     * 根据条件查询该用户领取的一卡通的总数
     *
     * @param queryBeans 查询实体
     * @param actionUser 执行用户
     * @return 数据总数
     */
    int countReceive(QueryBean[] queryBeans, UserBean actionUser);

    /**
     * 根据条件分页查询该用户领取的一卡通的
     *
     * @param queryBeans 查询实体
     * @param actionUser 执行用户
     * @param pageBean   分页对象
     * @param field      排序字段
     * @param desc       是否降序
     * @return 数据总数
     */
    List<CardBean> findReceiveByPage(QueryBean[] queryBeans, UserBean actionUser, PageBean pageBean, String field, boolean desc);
}
