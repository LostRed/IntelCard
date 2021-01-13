package com.lostred.ics.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间格式转换工具
 */
public class TimeUtil {
    /**
     * 将当天小时数重置为0点
     *
     * @param timestamp 时间戳
     * @return 时间戳
     */
    public static Timestamp roundToZeroHour(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(timestamp.getTime());
        Date date = null;
        try {
            date = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Timestamp(date.getTime());
    }

    /**
     * 将时间戳转换为时间字符串
     *
     * @param timestamp 时间戳
     * @return 时间字符串
     */
    public static String toDayString(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(timestamp.getTime());
    }

    /**
     * 将字符串时间转换为时间戳
     *
     * @param str 时间字符串
     * @return 时间戳
     */
    public static Timestamp toTimestamp(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Timestamp timestamp = null;
        try {
            Date date = sdf.parse(str);
            timestamp = new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
}
