package com.lostred.ics.service.impl;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Service;
import com.lostred.ics.bean.RecordBean;
import com.lostred.ics.dao.RecordDao;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.service.RecordService;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service("RecordService")
public class RecordServiceImpl extends BaseServiceImpl<RecordBean> implements RecordService {
    @Autowired
    private RecordDao recordDao;

    @Override
    public int countFindByCardId(int cardId) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            num = recordDao.countFindByCardId(conn, cardId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return num;
    }

    @Override
    public List<RecordBean> findByPageAndCardId(int cardId, PageBean pageBean) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<RecordBean> list = new ArrayList<>();
        try {
            list = recordDao.findByPageAndCardId(conn, cardId, pageBean);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }
}
