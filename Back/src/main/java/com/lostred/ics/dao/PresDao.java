package com.lostred.ics.dao;

import com.lostred.ics.bean.PresBean;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 处方dao接口
 */
public interface PresDao extends BaseDao<PresBean> {
    int deleteByCardId(Connection conn, int cardId) throws SQLException;
}
