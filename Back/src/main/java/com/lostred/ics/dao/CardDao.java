package com.lostred.ics.dao;

import com.lostred.ics.bean.CardBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 一卡通dao接口
 */
public interface CardDao extends BaseDao<CardBean> {
    /**
     * 删除待领取的一卡通
     *
     * @param conn   数据库连接对象
     * @param cardId 一卡通id
     * @return 受影响的数据数量
     */
    int deleteByCardId(Connection conn, int cardId) throws SQLException;

    /**
     * 批量插入一卡通
     *
     * @param conn      数据库连接对象
     * @param cardId    一卡通id
     * @param cardCodes 新增卡号集合
     * @return 受影响的数据数量
     */
    int batchInsert(Connection conn, int cardId, List<String> cardCodes) throws SQLException;

    /**
     * 查询卡号重复卡号数量
     *
     * @param conn          数据库连接对象
     * @param cardCodeStart 一卡通开始卡号
     * @param cardCodeEnd   一卡通截止卡号
     * @return 重复卡号集合
     */
    List<String> repeatCardCode(Connection conn, String cardCodeStart, String cardCodeEnd) throws SQLException;

    /**
     * 统计可注销一卡通的数量
     *
     * @param conn       数据库连接对象
     * @param queryBeans 查询对象数组
     * @return 可注销一卡通的总数
     */
    int countCancelable(Connection conn, QueryBean[] queryBeans) throws SQLException;

    /**
     * 查询可注销的一卡通
     *
     * @param conn       数据库连接对象
     * @param queryBeans 查询对象数组
     * @param pageBean   分页对象
     * @param field      排序字段
     * @param desc       是否降序排序
     * @return 可注销的一卡通集合
     */
    List<CardBean> findCancelByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException;

    /**
     * 注销一卡通
     *
     * @param conn   数据库连接对象
     * @param cardId 一卡通id
     * @return 受影响的数据数量
     */
    int cancel(Connection conn, int cardId) throws SQLException;

    /**
     * 将一个范围内待领用卡片的状态改为待出售
     *
     * @param conn          数据库连接对象
     * @param applyId       一卡通申请id
     * @param cardCodeStart 一卡通开始卡号
     * @param cardCodeEnd   一卡通截止卡号
     * @return 受影响的数据数量
     */
    int audit(Connection conn, int applyId, String cardCodeStart, String cardCodeEnd) throws SQLException;

    /**
     * 统计一个范围内待领用卡片的总数
     *
     * @param conn          数据库连接对象
     * @param cardCodeStart 一卡通开始卡号
     * @param cardCodeEnd   一卡通截止卡号
     * @return 受影响的数据数量
     */
    int countAvailable(Connection conn, String cardCodeStart, String cardCodeEnd) throws SQLException;

    /**
     * 查询申请单下的所有一卡通卡号
     *
     * @param conn    数据库连接对象
     * @param applyId 一卡通申请id
     * @return 审批通过的一卡通卡号集合
     */
    List<String> findByApplyId(Connection conn, int applyId) throws SQLException;

    /**
     * 将一卡通就诊人绑定
     *
     * @param conn       数据库连接对象
     * @param amount     预存金额
     * @param cardCode   一卡通卡号
     * @param patientId  就诊人id
     * @param actionUser 售卡用户
     * @return 受影响的数据数量
     */
    int sale(Connection conn, int amount, String cardCode, int patientId, UserBean actionUser) throws SQLException;

    /**
     * 查找一卡通卡号是否为待出售
     *
     * @param conn     数据库连接对象
     * @param cardCode 一卡通卡号
     * @return 是待出售则返回该卡对象，否则返回null
     */
    CardBean findSaleableByCardCode(Connection conn, String cardCode) throws SQLException;

    /**
     * 通过一卡通卡号查找已出售的一卡通
     *
     * @param conn     数据库连接对象
     * @param cardCode 可能是一卡通卡号的关键字
     * @return 一卡通对象
     */
    CardBean findSoldOutByCardCode(Connection conn, String cardCode) throws SQLException;

    /**
     * 通过就诊人id查找一卡通
     *
     * @param conn      数据库连接对象
     * @param PatientId 就诊人id
     * @return 一卡通对象
     */
    CardBean findByPatientId(Connection conn, int PatientId) throws SQLException;

    /**
     * 按条件查找一卡通总数
     *
     * @param conn       数据库连接对象
     * @param queryBeans 查询对象数组
     * @return 一卡通总数
     * @throws SQLException SQL异常
     */
    int countFind(Connection conn, QueryBean[] queryBeans) throws SQLException;

    /**
     * 快速分页查找一卡通(该方法生成的一卡通对象为贫血实体，字段属性不完全)
     *
     * @param conn       数据库连接对象
     * @param queryBeans 查询对象数组
     * @param pageBean   分页对象
     * @param field      排序字段
     * @param desc       是否降序
     * @return 一卡通集合
     * @throws SQLException SQL异常
     */
    List<CardBean> fastFindByPage(Connection conn, QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) throws SQLException;
}
