package com.zipe.base.formatter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.Formatter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

/**
 * 統一處理前端回傳Server的timeStamp日期字串
 *
 * @author adam.yeh
 */
public class DateFormatter implements Formatter<Date> {

    @Override
    public String print(Date date, Locale locale) {
        if (date == null) {
            return null;
        }

        return String.valueOf(date.getTime());
    }

    @Override
    public Date parse(String timestamp, Locale locale) {
        if (StringUtils.isEmpty(timestamp)) {
            return null;
        }

        return new Timestamp(Long.parseLong(timestamp));
    }

}
