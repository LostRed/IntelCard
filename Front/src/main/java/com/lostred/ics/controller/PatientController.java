package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.service.PatientService;

/**
 * 就诊人请求Controller
 */
@Controller("patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    /**
     * 根据身份证号查询该身份证是否存在
     *
     * @param idCard 身份证号码
     * @return 应答对象
     */
    @ReqMethod("findIdCard")
    public RespBean findIdCard(@ReqParam("idCard") String idCard) {
        boolean flag = patientService.findIdCard(idCard);
        RespBean respBean;
        if (!flag) {
            respBean = new RespBean(0, "non-exist", null, null);
        } else {
            respBean = new RespBean(1, "exist", null, null);
        }
        return respBean;
    }

    /**
     * 根据手机号码查询该手机号码是否存在
     *
     * @param phone 手机号码
     * @return 应答对象
     */
    @ReqMethod("findPhone")
    public RespBean findPhone(@ReqParam("phone") String phone) {
        boolean flag = patientService.findPhone(phone);
        RespBean respBean;
        if (!flag) {
            respBean = new RespBean(0, "non-exist", null, null);
        } else {
            respBean = new RespBean(1, "exist", null, null);
        }
        return respBean;
    }
}
