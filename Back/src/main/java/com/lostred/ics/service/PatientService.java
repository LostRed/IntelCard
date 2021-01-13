package com.lostred.ics.service;

import com.lostred.ics.bean.PatientBean;

/**
 * 就诊人业务接口
 */
public interface PatientService extends BaseService<PatientBean> {
    /**
     * 根据身份证号码查找该身份证是否存在
     *
     * @param idCard 身份证号码
     * @return 存在返回true，否则返回false
     */
    boolean findIdCard(String idCard);

    /**
     * 根据手机号码查找该手机号是否存在
     *
     * @param phone 手机号码
     * @return 存在返回true，否则返回false
     */
    boolean findPhone(String phone);
}
