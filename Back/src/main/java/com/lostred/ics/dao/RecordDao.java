package com.lostred.ics.dao;

import com.lostred.ics.bean.RecordBean;
import com.lostred.ics.query.PageBean;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 一卡通消费记录dao接口
 */
public interface RecordDao extends BaseDao<RecordBean> {
    /**
     * 把旧卡id的记录改为新卡id
     *
     * @param conn      数据库连接对象
     * @param oldCardId 旧卡id
     * @param newCardId 新卡id
     * @return 受影响的记录数量
     * @throws SQLException SQL异常
     */
    int changeRecord(Connection conn, int oldCardId, int newCardId) throws SQLException;

    /**
     * 删除一卡通记录
     *
     * @param conn   数据库连接对象
     * @param cardId 一卡通id
     * @return 受影响的记录数量
     * @throws SQLException SQL异常
     */
    int deleteByCardId(Connection conn, int cardId) throws SQLException;

    /**
     * 查询消费记录的数据总数
     *
     * @param conn 数据库连接对象
     * @return 数据总数
     * @throws SQLException SQL异常
     */
    int countFindByCardId(Connection conn, int cardId) throws SQLException;

    /**
     * 分页查询消费记录(根据记录时间降序排序)
     *
     * @param conn     数据库连接对象
     * @param pageBean 页码实体
     * @return 数据持久化实体集合
     * @throws SQLException SQL异常
     */
    List<RecordBean> findByPageAndCardId(Connection conn, int cardId, PageBean pageBean) throws SQLException;
}
