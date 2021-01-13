package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.bean.CardBean;
import com.lostred.ics.bean.PresBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.service.PresService;
import com.lostred.ics.servlet.Servlet;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * 处方请求Controller
 */
@Controller("pres")
public class PresController {
    @Autowired
    private PresService presService;

    /**
     * 新增处方
     *
     * @param presString 处方json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("insertPres")
    public RespBean insertPres(@ReqParam("pres") String presString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        PresBean presBean = mapper.readValue(presString, PresBean.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = presService.insert(presBean, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 根据一卡通id分页查询处方
     *
     * @param currentPageString 当前页json
     * @param pageSizeString    页面行数json
     * @param cardIdString      一卡通id json
     * @return 应答对象
     */
    @ReqMethod("findByCardId")
    public RespBean findByCardId(@ReqParam("currentPage") String currentPageString,
                                 @ReqParam("pageSize") String pageSizeString,
                                 @ReqParam("cardId") String cardIdString) {
        PageBean pageBean = PageBean.createPageBean(currentPageString, pageSizeString);
        int cardId = Integer.parseInt(cardIdString);
        QueryBean[] queryBeans = new QueryBean[2];
        queryBeans[0] = new QueryBean("CARD_ID", "=", cardId);
        queryBeans[1] = new QueryBean("PARAM_STATE_ID", "=", 16);
        int count = presService.countFindBean(queryBeans);
        pageBean.setCount(count);
        List<PresBean> presList = presService.findBeanByPage(queryBeans, pageBean, "PRES_ID", false);
        return new RespBean(0, null, pageBean, presList);
    }

    /**
     * 处方退费
     *
     * @param cardString      一卡通json
     * @param presBeansString 处方json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("refund")
    public RespBean refund(@ReqParam("card") String cardString,
                           @ReqParam("presBeans") String presBeansString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CardBean cardBean = mapper.readValue(cardString, CardBean.class);
        PresBean[] presBeans = mapper.readValue(presBeansString, PresBean[].class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = presService.refund(cardBean, presBeans, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }
}
