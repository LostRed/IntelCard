package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.bean.ParamBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.service.ParamService;
import com.lostred.ics.servlet.Servlet;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 参数请求Controller
 */
@Controller("param")
public class ParamController {
    @Autowired
    private ParamService paramService;

    /**
     * 按照参数类型查询参数
     *
     * @param paramType 参数类型
     * @return 应答对象
     * @throws SQLException IO异常
     */
    @ReqMethod("findByType")
    public RespBean findByType(@ReqParam("paramType") String paramType) throws SQLException {
        RespBean respBean;
        if (paramType != null && !"".equals(paramType)) {
            List<ParamBean> paramList = paramService.findByType(paramType);
            respBean = new RespBean(0, "success", null, paramList);
        } else {
            respBean = new RespBean(1, "fail", null, null);
        }
        return respBean;
    }

    /**
     * 查询所有可用的的一卡通状态
     *
     * @param paramValue 参数值
     * @return 应答对象
     * @throws SQLException IO异常
     */
    @ReqMethod("findCardState")
    public RespBean findCardState(@ReqParam("paramValue") String paramValue) throws SQLException {
        RespBean respBean;
        if (paramValue != null && !"".equals(paramValue)) {
            List<ParamBean> paramList = paramService.findCardState(Integer.parseInt(paramValue));
            respBean = new RespBean(0, "success", null, paramList);
        } else {
            respBean = new RespBean(1, "fail", null, null);
        }
        return respBean;
    }

    /**
     * 查询所有可用的的用户状态
     *
     * @param paramValue 用户状态
     * @return 应答对象
     * @throws SQLException IO异常
     */
    @ReqMethod("findUserState")
    public RespBean findUserState(@ReqParam("paramValue") String paramValue) throws SQLException {
        RespBean respBean;
        if (paramValue != null && !"".equals(paramValue)) {
            List<ParamBean> paramList = paramService.findUserState(Integer.parseInt(paramValue));
            respBean = new RespBean(0, "success", null, paramList);
        } else {
            respBean = new RespBean(1, "fail", null, null);
        }
        return respBean;
    }

    /**
     * 查询所有可用的的用户状态
     *
     * @return 应答对象
     */
    @ReqMethod("findParamType")
    public RespBean findParamType() {
        List<String> paramTypeList = paramService.findAllParamType();
        return new RespBean(0, "success", null, paramTypeList);
    }

    /**
     * 分页查询用户
     *
     * @param currentPageString 当前页json
     * @param pageSizeString    页面行数json
     * @param queryBeansString  查询条件对象json
     * @param field             排序列名
     * @param descString        是否降序
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
        int count = paramService.countFindBean(queryBeans);
        pageBean.setCount(count);
        List<ParamBean> paramList = paramService.findBeanByPage(queryBeans, pageBean, field, desc);
        return new RespBean(0, null, pageBean, paramList);
    }

    /**
     * 更改参数状态为不可用
     *
     * @param paramIdsString 参数id数组json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("changeParam")
    public RespBean changeParam(@ReqParam("paramIds") String paramIdsString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        int[] paramIds = mapper.readValue(paramIdsString, int[].class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = paramService.changeParam(paramIds, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 新增参数
     *
     * @param paramNameString  参数名json
     * @param paramTypeString  参数类型json
     * @param paramValueString 参数值json
     * @return 应答对象
     */
    @ReqMethod("insertParam")
    public RespBean insertParam(@ReqParam("paramName") String paramNameString,
                               @ReqParam("paramType") String paramTypeString,
                               @ReqParam("paramValue") String paramValueString) {
        ParamBean paramBean = new ParamBean();
        paramBean.setParamName(paramNameString);
        paramBean.setParamType(paramTypeString);
        paramBean.setParamValue(Integer.parseInt(paramValueString));
        paramBean.setParamState("可用");
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = paramService.insertBean(paramBean, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 修改用户
     *
     * @param paramString 参数json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("updateParam")
    public RespBean updateParam(@ReqParam("param") String paramString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ParamBean paramBean = mapper.readValue(paramString, ParamBean.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = paramService.updateBean(paramBean, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }
}
