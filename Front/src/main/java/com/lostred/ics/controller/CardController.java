package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.bean.CardBean;
import com.lostred.ics.bean.PatientBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.service.CardService;
import com.lostred.ics.servlet.Servlet;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * 一卡通请求Controller
 */
@Controller("card")
public class CardController {
    @Autowired
    private CardService cardService;

    /**
     * 分页查找一卡通
     *
     * @param currentPageString 当前页json
     * @param pageSizeString    页面行数json
     * @param queryBeansString  查询对象数组json
     * @param field             排序列名
     * @param descString        是否降序json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("findByPage")
    public RespBean findByPage(@ReqParam("currentPage") String currentPageString,
                               @ReqParam("pageSize") String pageSizeString,
                               @ReqParam("queryBeans") String queryBeansString,
                               @ReqParam("field") String field,
                               @ReqParam("desc") String descString) throws IOException {
        PageBean pageBean = PageBean.createPageBean(currentPageString, pageSizeString);
        ObjectMapper mapper = new ObjectMapper();
        QueryBean[] queryBeans = mapper.readValue(queryBeansString, QueryBean[].class);
        boolean desc = mapper.readValue(descString, boolean.class);
        int count = cardService.countFindBean(queryBeans);
        pageBean.setCount(count);
        List<CardBean> cardList = cardService.fastFindByPage(queryBeans, pageBean, field, desc);
        return new RespBean(0, null, pageBean, cardList);
    }


    /**
     * 分页查找可注销的一卡通
     *
     * @param currentPageString 当前页json
     * @param pageSizeString    页面行数json
     * @param queryBeansString  查询对象数组json
     * @param field             排序列名
     * @param descString        是否降序json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("findCancelByPage")
    public RespBean findCancelByPage(@ReqParam("currentPage") String currentPageString,
                                     @ReqParam("pageSize") String pageSizeString,
                                     @ReqParam("queryBeans") String queryBeansString,
                                     @ReqParam("field") String field,
                                     @ReqParam("desc") String descString) throws IOException {
        PageBean pageBean = PageBean.createPageBean(currentPageString, pageSizeString);
        ObjectMapper mapper = new ObjectMapper();
        QueryBean[] queryBeans = mapper.readValue(queryBeansString, QueryBean[].class);
        boolean desc = mapper.readValue(descString, boolean.class);
        int count = cardService.countCancel(queryBeans);
        pageBean.setCount(count);
        List<CardBean> cardList = cardService.findCancelByPage(queryBeans, pageBean, field, desc);
        return new RespBean(0, null, pageBean, cardList);
    }

    /**
     * 根据领卡申请id查询一卡通
     *
     * @param applyIdString 领卡申请id json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("findByApplyId")
    public RespBean findByApplyId(@ReqParam("applyId") String applyIdString) throws IOException {
        int applyId = Integer.parseInt(applyIdString);
        List<String> cardList = cardService.findByApplyId(applyId);
        return new RespBean(0, null, null, cardList);
    }

    /**
     * 逻辑删除一卡通
     *
     * @param cardIdsString 一卡通id json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("deleteCard")
    public RespBean deleteCard(@ReqParam("cardIds") String cardIdsString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        int[] cardIds = mapper.readValue(cardIdsString, int[].class);
        int total = cardIds.length;
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = cardService.deleteById(cardIds, actionUser);
        RespBean respBean;
        if (num > 0 && num == total) {
            respBean = new RespBean(0, "", null, num);
        } else if (num > 0 && num != total) {
            respBean = new RespBean(0, "检测到有" + (total - num) + "张卡无法删除，", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 新增一卡通
     *
     * @param cardCodeStartString 一卡通开始卡号json
     * @param cardCodeEndString   一卡通截止卡号json
     * @param prefixString        一卡通前缀json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("insertCard")
    public RespBean insertCard(@ReqParam("cardCodeStart") String cardCodeStartString,
                               @ReqParam("cardCodeEnd") String cardCodeEndString,
                               @ReqParam("prefix") String prefixString) throws IOException {
        int cardCodeStart = Integer.parseInt(cardCodeStartString);
        int cardCodeEnd = Integer.parseInt(cardCodeEndString);
        int total = cardCodeEnd - cardCodeStart + 1;
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = cardService.batchInsert(prefixString.trim().toUpperCase(), cardCodeStart, cardCodeEnd, actionUser);
        RespBean respBean;
        if (num > 0 && num == total) {
            respBean = new RespBean(0, "", null, num);
        } else if (num > 0 && num != total) {
            respBean = new RespBean(0, "检测到有" + (total - num) + "张卡重复，", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 注销一卡通
     *
     * @param cardIdsString 一卡通卡号json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("cancelCard")
    public RespBean cancelCard(@ReqParam("cardIds") String cardIdsString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        int[] cardIds = mapper.readValue(cardIdsString, int[].class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = cardService.cancelById(cardIds, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 售卡
     *
     * @param patientString 就诊人json
     * @param amountString  预存金额json
     * @param cardCode      一卡通卡号
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("saleCard")
    public RespBean saleCard(@ReqParam("patient") String patientString,
                             @ReqParam("amount") String amountString,
                             @ReqParam("cardCode") String cardCode) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PatientBean patientBean = mapper.readValue(patientString, PatientBean.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int amount = Integer.parseInt(amountString);
        int num = cardService.saleByCardCode(patientBean, amount, cardCode, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 根据一卡通卡号查询该卡是否是可出售状态
     *
     * @param cardCode 一卡通卡号
     * @return 应答对象
     */
    @ReqMethod("findSaleableByCardCode")
    public RespBean findSaleableByCardCode(@ReqParam("cardCode") String cardCode) {
        CardBean cardBean = cardService.findSaleableByCardCode(cardCode);
        RespBean respBean;
        if (cardBean != null) {
            respBean = new RespBean(0, "yes", null, cardBean);
        } else {
            respBean = new RespBean(1, "no", null, null);
        }
        return respBean;
    }


//    @ReqMethod("changCard")
//    public RespBean changCard(@ReqParam("patient") String patientString,
//                              @ReqParam("amount") String amountString,
//                              @ReqParam("cardCode") String cardCode) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        PatientBean patientBean = mapper.readValue(patientString, PatientBean.class);
//        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
//        int amount = Integer.parseInt(amountString);
//        int num = cardService.saleByCardCode(patientBean, amount, cardCode, actionUser);
//        RespBean respBean;
//        if (num > 0) {
//            respBean = new RespBean(0, "success", null, num);
//        } else {
//            respBean = new RespBean(1, "fail", null, num);
//        }
//        return respBean;
//    }

    /**
     * 查找已出售的一卡通
     *
     * @param keyword 关键字(一卡通卡号、身份证号、手机号)
     * @return 应答对象
     */
    @ReqMethod("findSoldOut")
    public RespBean findSoldOut(@ReqParam("keyword") String keyword) {
        CardBean cardBean = cardService.findSoldOut(keyword);
        RespBean respBean;
        if (cardBean != null) {
            respBean = new RespBean(0, "success", null, cardBean);
        } else {
            respBean = new RespBean(1, "fail", null, null);
        }
        return respBean;
    }

    /**
     * 换卡
     *
     * @param oldCardString 旧卡json
     * @param cardCode      新卡卡号
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("changeCard")
    public RespBean changeCard(@ReqParam("oldCard") String oldCardString,
                               @ReqParam("cardCode") String cardCode) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CardBean oldCard = mapper.readValue(oldCardString, CardBean.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = cardService.changCard(oldCard, cardCode, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 退卡
     *
     * @param oldCardString 旧卡json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("refundCard")
    public RespBean refundCard(@ReqParam("oldCard") String oldCardString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CardBean oldCard = mapper.readValue(oldCardString, CardBean.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = cardService.refundCard(oldCard, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 一卡通充值
     *
     * @param oldCardString  旧卡json
     * @param rechargeString 充值金额json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("rechargeCard")
    public RespBean rechargeCard(@ReqParam("oldCard") String oldCardString,
                                 @ReqParam("recharge") String rechargeString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CardBean oldCard = mapper.readValue(oldCardString, CardBean.class);
        int recharge = Integer.parseInt(rechargeString);
        int num = cardService.rechargeCard(oldCard, recharge);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 分页查询登录用户领取的一卡通
     *
     * @param currentPageString 当前页json
     * @param pageSizeString    页面行数json
     * @param queryBeansString  查询对象数组json
     * @param field             排序列名
     * @param descString        是否降序json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("findReceiveByPage")
    public RespBean findReceiveByPage(@ReqParam("currentPage") String currentPageString,
                                      @ReqParam("pageSize") String pageSizeString,
                                      @ReqParam("queryBeans") String queryBeansString,
                                      @ReqParam("field") String field,
                                      @ReqParam("desc") String descString) throws IOException {
        PageBean pageBean = PageBean.createPageBean(currentPageString, pageSizeString);
        ObjectMapper mapper = new ObjectMapper();
        QueryBean[] queryBeans = mapper.readValue(queryBeansString, QueryBean[].class);
        boolean desc = mapper.readValue(descString, boolean.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int count = cardService.countReceive(queryBeans, actionUser);
        pageBean.setCount(count);
        List<CardBean> cardList = cardService.findReceiveByPage(queryBeans, actionUser, pageBean, field, desc);
        return new RespBean(0, null, pageBean, cardList);
    }
}
