package com.bsoft.template.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * 日期工具
 * @author artolia
 */
public class DateUtil {

    /**
     * Date转换为LocalDate
     * @param date Date类型日期
     * @return LocalDate
     */
    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * LocalDate转换为Date
     * @param localDate LocalDate类型日期
     * @return Date
     */
    public static Date localDate2Date(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);

        System.out.println(Date.from(zdt.toInstant()));
        return Date.from(zdt.toInstant());
    }

}
