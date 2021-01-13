package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.bean.RecordBean;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.query.PageBean;
import com.lostred.ics.service.RecordService;

import java.util.List;

/**
 * 一卡通消费记录请求Controller
 */
@Controller("record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    /**
     * 根据一卡通id分页查询消费记录
     *
     * @param currentPageString 当前页json
     * @param pageSizeString    页面行数json
     * @param cardIdString      一卡通id json
     * @return 应答对象
     */
    @ReqMethod("findByPage")
    public RespBean findByPage(@ReqParam("currentPage") String currentPageString,
                               @ReqParam("pageSize") String pageSizeString,
                               @ReqParam("cardId") String cardIdString) {
        PageBean pageBean = PageBean.createPageBean(currentPageString, pageSizeString);
        int cardId = Integer.parseInt(cardIdString);
        int count = recordService.countFindByCardId(cardId);
        pageBean.setCount(count);
        List<RecordBean> recordList = recordService.findByPageAndCardId(cardId, pageBean);
        return new RespBean(0, null, pageBean, recordList);
    }
}
