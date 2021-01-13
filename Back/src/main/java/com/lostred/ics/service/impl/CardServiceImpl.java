package com.lostred.ics.service.impl;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Service;
import com.lostred.ics.bean.*;
import com.lostred.ics.dao.*;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.service.CardService;
import com.lostred.ics.util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("CardService")
public class CardServiceImpl extends BaseServiceImpl<CardBean> implements CardService {
    @Autowired
    private CardDao cardDao;
    @Autowired
    private PatientDao patientDao;
    @Autowired
    private ParamDao paramDao;
    @Autowired
    private RecordDao recordDao;
    @Autowired
    private ApplyDao applyDao;
    @Autowired
    private PresDao presDao;

    @Override
    public int deleteById(int[] cardIds, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            for (int cardId : cardIds) {
                num += cardDao.deleteByCardId(conn, cardId);
            }
            if (num > 0) {
                LogBean logBean = new LogBean();
                logBean.setActionUser(actionUser);
                logBean.setLogName("删除一卡通");
                logBean.setDescInfo("删除了" + num + "条数据");
                logDao.insert(conn, logBean);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            num = 0;
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return num;
    }

    @Override
    public int batchInsert(String prefix, int cardCodeStart, int cardCodeEnd, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        String cardCodeStartString = prefix + String.format("%08d", cardCodeStart);
        String cardCodeEndString = prefix + String.format("%08d", cardCodeEnd);
        int num = 0;
        try {
            conn.setAutoCommit(false);
            List<String> repeatList = cardDao.repeatCardCode(conn, cardCodeStartString, cardCodeEndString);
            int cardCodeNum = cardCodeStart;
            List<String> cardCodes = new ArrayList<>();
            do {
                String cardCodeNumString = prefix + String.format("%08d", cardCodeNum);
                boolean flag = false;
                for (String s : repeatList) {
                    if (s.equals(cardCodeNumString)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    cardCodes.add(cardCodeNumString);
                }
                cardCodeNum++;
            } while (cardCodeNum <= cardCodeEnd);
            if (cardCodes.size() != 0) {
                int cardId = cardDao.getNewId(conn);
                num += cardDao.batchInsert(conn, cardId, cardCodes);
                for (int i = 0; i < num - 1; i++) {
                    cardDao.getNewId(conn);
                }
                LogBean logBean = new LogBean();
                logBean.setActionUser(actionUser);
                logBean.setLogName("批量新增一卡通");
                logBean.setDescInfo("新增了" + num + "条数据");
                logDao.insert(conn, logBean);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            num = 0;
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return num;
    }

    @Override
    public int countCancel(QueryBean[] queryBeans) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int count = 0;
        try {
            count = cardDao.countCancelable(conn, queryBeans);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return count;
    }

    @Override
    public List<CardBean> findCancelByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<CardBean> list = new ArrayList<>();
        try {
            list = cardDao.findCancelByPage(conn, queryBeans, pageBean, field, desc);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public int cancelById(int[] cardIds, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            for (int cardId : cardIds) {
                num += cardDao.cancel(conn, cardId);
            }
            if (num > 0) {
                LogBean logBean = new LogBean();
                logBean.setActionUser(actionUser);
                logBean.setLogName("注销一卡通");
                logBean.setDescInfo("修改了" + num + "条数据");
                logDao.insert(conn, logBean);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            num = 0;
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return num;
    }

    @Override
    public List<String> findByApplyId(int applyId) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<String> list = new ArrayList<>();
        try {
            list = cardDao.findByApplyId(conn, applyId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public int saleByCardCode(PatientBean patientBean, int amount, String cardCode, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            int patientId = patientDao.getNewId(conn);
            num += cardDao.sale(conn, amount - 5, cardCode, patientId, actionUser);
            if (num > 0) {
                patientBean.setPatientId(patientId);
                patientDao.insert(conn, patientBean);
                RecordBean recordBean = new RecordBean();
                recordBean.setActionUser(actionUser);
                recordBean.setCardBean(cardDao.findByPatientId(conn, patientId));
                recordBean.setName(paramDao.findBeanById(conn, 11));
                recordBean.setAmount(amount);
                recordDao.insertBean(conn, recordBean);
                LogBean logBean = new LogBean();
                logBean.setActionUser(actionUser);
                logBean.setLogName("出售一卡通");
                logBean.setDescInfo("出售的一卡通卡号为：" + cardCode);
                logDao.insert(conn, logBean);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            num = 0;
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return num;
    }

    @Override
    public CardBean findSaleableByCardCode(String cardCode) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        CardBean cardBean = null;
        try {
            cardBean = cardDao.findSaleableByCardCode(conn, cardCode);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return cardBean;
    }

    @Override
    public CardBean findSoldOut(String keyword) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        CardBean cardBean = null;
        try {
            cardBean = cardDao.findSoldOutByCardCode(conn, keyword);
            if (cardBean == null) {
                PatientBean patientBean = patientDao.findByIdCardOrPhone(conn, keyword);
                if (patientBean != null) {
                    cardBean = cardDao.findByPatientId(conn, patientBean.getPatientId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return cardBean;
    }

    @Override
    public int changCard(CardBean oldCard, String cardCode, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            CardBean newCard = cardDao.findSaleableByCardCode(conn, cardCode);
            newCard.setPatientBean(oldCard.getPatientBean());
            newCard.setAmount(oldCard.getAmount());
            newCard.setDeposit(oldCard.getDeposit());
            newCard.setSaleUser(actionUser);
            newCard.setSaleTime(new Timestamp(System.currentTimeMillis()));
            newCard.setDescInfo(oldCard.getDescInfo());
            newCard.setState(paramDao.findBeanById(conn, 5));
            oldCard.setState(paramDao.findBeanById(conn, 8));
            oldCard.setAmount(0);
            oldCard.setDeposit(0);
            oldCard.setPatientBean(null);
            num += cardDao.updateBean(conn, newCard);
            num += cardDao.updateBean(conn, oldCard);
            num += recordDao.changeRecord(conn, oldCard.getCardId(), newCard.getCardId());
            if (num > 0) {
                LogBean logBean = new LogBean();
                logBean.setActionUser(actionUser);
                logBean.setLogName("更换一卡通");
                logBean.setDescInfo("更换的一卡通卡号为：" + newCard.getCardCode() + "，旧卡号为：" + oldCard.getCardCode());
                logDao.insert(conn, logBean);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            num = 0;
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return num;
    }

    @Override
    public int refundCard(CardBean oldCard, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            oldCard.setState(paramDao.findBeanById(conn, 7));
            oldCard.setSaleTime(null);
            oldCard.setSaleUser(null);
            int patientId = oldCard.getPatientBean().getPatientId();
            patientDao.deleteBeanById(conn, patientId);
            oldCard.setPatientBean(null);
            oldCard.setAmount(0);
            oldCard.setDeposit(0);
            oldCard.setDescInfo("");
            num += cardDao.updateBean(conn, oldCard);
            num += recordDao.deleteByCardId(conn, oldCard.getCardId());
            num += presDao.deleteByCardId(conn, oldCard.getCardId());
            if (num > 0) {
                LogBean logBean = new LogBean();
                logBean.setActionUser(actionUser);
                logBean.setLogName("退卡");
                logBean.setDescInfo("退卡的一卡通卡号为：" + oldCard.getCardCode());
                logDao.insert(conn, logBean);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            num = 0;
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return num;
    }

    @Override
    public int rechargeCard(CardBean oldCard, int recharge) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int num = 0;
        try {
            conn.setAutoCommit(false);
            oldCard.setAmount(oldCard.getAmount() + recharge);
            num += cardDao.updateBean(conn, oldCard);
            RecordBean recordBean = new RecordBean();
            recordBean.setCardBean(oldCard);
            recordBean.setName(paramDao.findBeanById(conn, 12));
            recordBean.setAmount(recharge);
            num += recordDao.insertBean(conn, recordBean);
            if (num > 0) {
                LogBean logBean = new LogBean();
                logBean.setLogName("一卡通充值");
                logBean.setDescInfo("操作人：" + oldCard.getPatientBean().getName() + "，充值金额：" + recharge);
                logDao.insert(conn, logBean);
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            num = 0;
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return num;
    }

    @Override
    public List<CardBean> fastFindByPage(QueryBean[] queryBeans, PageBean pageBean, String field, boolean desc) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<CardBean> list = new ArrayList<>();
        try {
            list = cardDao.fastFindByPage(conn, queryBeans, pageBean, field, desc);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }

    @Override
    public int countReceive(QueryBean[] queryBeans, UserBean actionUser) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        int total = 0;
        try {
            QueryBean[] newQueryBeans = Arrays.copyOf(queryBeans, queryBeans.length + 1);
            newQueryBeans[newQueryBeans.length - 1] = new QueryBean("APPLY_USER_ID", "=", actionUser.getUserId());
            total = cardDao.countFind(conn, newQueryBeans);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return total;
    }

    @Override
    public List<CardBean> findReceiveByPage(QueryBean[] queryBeans, UserBean actionUser, PageBean pageBean, String field, boolean desc) {
        Connection conn = JdbcUtil.getInstance().getConnection();
        List<CardBean> list = new ArrayList<>();
        try {
            QueryBean[] newQueryBeans = Arrays.copyOf(queryBeans, queryBeans.length + 1);
            newQueryBeans[newQueryBeans.length - 1] = new QueryBean("APPLY_USER_ID", "=", actionUser.getUserId());
            list = cardDao.fastFindByPage(conn, newQueryBeans, pageBean, field, desc);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.getInstance().close(conn);
        }
        return list;
    }
}
