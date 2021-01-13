package com.lostred.ics.controller;

import com.lostred.ics.annotation.Autowired;
import com.lostred.ics.annotation.Controller;
import com.lostred.ics.annotation.ReqMethod;
import com.lostred.ics.annotation.ReqParam;
import com.lostred.ics.bean.AppointBean;
import com.lostred.ics.bean.CardBean;
import com.lostred.ics.bean.UserBean;
import com.lostred.ics.dto.DayWorkBean;
import com.lostred.ics.dto.RespBean;
import com.lostred.ics.service.AppointService;
import com.lostred.ics.servlet.Servlet;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 排班和预约请求Controller
 */
@Controller("appoint")
public class AppointController {
    @Autowired
    private AppointService appointService;

    /**
     * 查找所有排班
     *
     * @return 应答对象
     */
    @ReqMethod("findAll")
    public RespBean findAll() {
        List<AppointBean> appointList = appointService.findAllBean();
        return new RespBean(0, null, null, appointList);
    }

    /**
     * 新增排班
     *
     * @param doctorString   医生用户json
     * @param workTimeString 就诊时间json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("insertAppoint")
    public RespBean insertAppoint(@ReqParam("doctor") String doctorString,
                                  @ReqParam("workTime") String workTimeString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserBean doctor = mapper.readValue(doctorString, UserBean.class);
        Timestamp workTime = mapper.readValue(workTimeString, Timestamp.class);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        AppointBean appointBean = appointService.insertAppoint(doctor, workTime, actionUser);
        RespBean respBean;
        if (appointBean != null) {
            respBean = new RespBean(0, "success", null, appointBean);
        } else {
            respBean = new RespBean(1, "fail", null, null);
        }
        return respBean;
    }

    /**
     * 删除排班
     *
     * @param appointIdString 预约json
     * @return 应答对象
     */
    @ReqMethod("deleteAppoint")
    public RespBean deleteAppoint(@ReqParam("appointId") String appointIdString) {
        int appointId = Integer.parseInt(appointIdString);
        UserBean actionUser = (UserBean) Servlet.SESSION.getAttribute("loginUser");
        int num = appointService.deleteAppoint(appointId, actionUser);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 按照每周查找排班
     *
     * @param currentFirstDateString 每周的第一天日期json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("findByWeek")
    public RespBean findByWeek(@ReqParam("currentFirstDate") String currentFirstDateString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Timestamp currentFirstDate = mapper.readValue(currentFirstDateString, Timestamp.class);
        List<DayWorkBean> list = appointService.findAppointByWeek(currentFirstDate);
        return new RespBean(0, null, null, list);
    }

    /**
     * 按照日期和医生查找排班
     *
     * @param doctorString 医生用户json
     * @param dateString   日期json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("findByDoctorAndDate")
    public RespBean findByDoctorAndDate(@ReqParam("doctor") String doctorString,
                                        @ReqParam("date") String dateString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserBean doctor = mapper.readValue(doctorString, UserBean.class);
        Timestamp date = mapper.readValue(dateString, Timestamp.class);
        List<AppointBean> appointList = appointService.findByDoctorAndDate(doctor, date);
        return new RespBean(0, null, null, appointList);
    }

    /**
     * 就诊人预约
     *
     * @param cardString    一卡通json
     * @param appointString 排班json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("register")
    public RespBean register(@ReqParam("card") String cardString,
                             @ReqParam("appoint") String appointString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CardBean cardBean = mapper.readValue(cardString, CardBean.class);
        AppointBean appointBean = mapper.readValue(appointString, AppointBean.class);
        int num = appointService.register(cardBean, appointBean);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 就诊人撤销预约
     *
     * @param appointString 排班json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("cancel")
    public RespBean cancel(@ReqParam("card") String cardString,
                           @ReqParam("appoint") String appointString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CardBean cardBean = mapper.readValue(cardString, CardBean.class);
        AppointBean appointBean = mapper.readValue(appointString, AppointBean.class);
        int num = appointService.cancel(cardBean, appointBean);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }

    /**
     * 按照就诊人id查找预约
     *
     * @param patientIdString 就诊人id json
     * @return 应答对象
     */
    @ReqMethod("findAppointByPatientId")
    public RespBean findAppointByPatientId(@ReqParam("patientId") String patientIdString) {
        int patientId = Integer.parseInt(patientIdString);
        List<AppointBean> appointList = appointService.findByPatientId(patientId);
        return new RespBean(0, null, null, appointList);
    }

    /**
     * 就诊人取号
     *
     * @param appointIdsString 排班json
     * @return 应答对象
     * @throws IOException IO异常
     */
    @ReqMethod("offer")
    public RespBean offer(@ReqParam("appointIds") String appointIdsString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        int[] appointIds = mapper.readValue(appointIdsString, int[].class);
        int num = appointService.offer(appointIds);
        RespBean respBean;
        if (num > 0) {
            respBean = new RespBean(0, "success", null, num);
        } else {
            respBean = new RespBean(1, "fail", null, num);
        }
        return respBean;
    }
}
