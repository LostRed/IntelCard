package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.bean.LogBean;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.service.LogService;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * 日志请求Controller
 */
@Controller("log")
public class LogController {
    @Autowired
    private LogService logService;

    /**
     * 分页查询日志
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
        int count = logService.countFindBean(queryBeans);
        pageBean.setCount(count);
        List<LogBean> logList = logService.fastFindByPage(queryBeans, pageBean, field, desc);
        return new RespBean(0, null, pageBean, logList);
    }
}
