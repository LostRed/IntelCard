package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.bean.ApplyBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dao.ParamDao;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.service.ApplyService;
import com.lostred.ics.servlet.Servlet;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * 领卡申请请求Controller
 */
@Controller("apply")
public class ApplyController {
    @Autowired
    private ApplyService applyService;
    @Autowired
    private ParamDao paramDao;

    /**
     * 查询登录用户
     *
     * @return 应答对象
     */
    @ReqMethod("loginUser")
    public RespBean loginUser() {
        Object loginUser = Servlet.SESSION.getAttribute("loginUser");
        return new RespBean(0, null, null, loginUser);
    }

    /**
     * 新增领卡申请
     *
     * @param applyNumString 申请数量json
     * @return 应答对象
     */
    @ReqMethod("insertApply")
    public RespBean insertApply(@ReqParam("applyNum") String applyNumString) {
        int applyNum = Integer.parseInt(applyNumString);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = applyService.insertApply(applyNum, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 修改领卡申请
     *
     * @param applyString 领卡申请json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("updateApply")
    public RespBean updateApply(@ReqParam("apply") String applyString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ApplyBean applyBean = mapper.readValue(applyString, ApplyBean.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = applyService.updateBean(applyBean, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 分页查询领卡申请
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
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        for (QueryBean queryBean : queryBeans) {
            if (queryBean.getField().equals("APPLY_USER_ID") && queryBean.getValue().equals("loginUser")) {
                queryBean.setValue(actionUser.getUserId());
            }
        }
        boolean desc = mapper.readValue(descString, boolean.class);
        int count = applyService.countFindBean(queryBeans);
        pageBean.setCount(count);
        List<ApplyBean> applyList = applyService.fastFindBeanByPage(queryBeans, pageBean, field, desc);
        return new RespBean(0, null, pageBean, applyList);
    }

    /**
     * 删除领卡申请
     *
     * @param applyIdsString 领卡申请json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("deleteApply")
    public RespBean deleteApply(@ReqParam("applyIds") String applyIdsString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        int[] applyIds = mapper.readValue(applyIdsString, int[].class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = applyService.batchDeleteBean(applyIds, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 审批通过
     *
     * @param applyString         领卡申请json
     * @param cardCodeStartString 一卡通开始卡号json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("auditApply")
    public RespBean auditApply(@ReqParam("apply") String applyString,
                               @ReqParam("cardCodeStart") String cardCodeStartString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ApplyBean applyBean = mapper.readValue(applyString, ApplyBean.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = applyService.auditApply(applyBean, cardCodeStartString, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }
}
