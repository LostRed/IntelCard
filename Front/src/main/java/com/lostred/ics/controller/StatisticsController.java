package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.dto.StatisticsBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.query.QueryBean;
import com.lostred.ics.service.LogService;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * 工作量统计请求Controller
 */
@Controller("statistics")
public class StatisticsController {
    @Autowired
    private LogService logService;

    /**
     * 分页查询工作量统计
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
        int count = logService.countFindStatistics(queryBeans);
        pageBean.setCount(count);
        List<StatisticsBean> statisticsList = logService.findStatisticsByPage(queryBeans, pageBean, "NLSSORT(" + field + ",'NLS_SORT=SCHINESE_PINYIN_M')", desc);
        return new RespBean(0, null, pageBean, statisticsList);
    }
}
