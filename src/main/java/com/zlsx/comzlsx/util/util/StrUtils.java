/**
 * 文件名：Base64Utils.java
 *
 * @Package com.lzt.common.util
 * @Description: TODO(这里用一句话描述这个方法的作用)
 * 版本信息： 0
 * 日期：20--25
 */

package com.zlsx.comzlsx.util.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: StrUtils
 * @Description: TODO
 * @author: wangzj@lzt.com.cn
 * @version: 20--25 上午11:28:42
 */

public class StrUtils {

    /**
     * null转为""
     */
    public static String null2String(String str) {
        if (null == str) {
            str = "";
        }
        return str;
    }

    public static int getIntervalDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new RuntimeException("date1 is null or date2 is null");
        }
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date1);
//		int day1 = calendar.get(Calendar.DAY_OF_YEAR);
//		calendar.setTime(date2);
//		int day2 = calendar.get(Calendar.DAY_OF_YEAR);
//		return (day1-day2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = sdf.parse(sdf.format(date1));
            date2 = sdf.parse(sdf.format(date2));
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(date2);
            long time2 = cal.getTimeInMillis();
            long between_days = (time1 - time2) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            // TODO Auto-generated catch block

        }
        return -1;
    }

    public static String replaceLineBlanks(String str) {
        String result = "";
        if (str != null) {
            Pattern p = Pattern.compile("(\r?\n(\\s*\r?\n)+)");
            Matcher m = p.matcher(str);
            result = m.replaceAll("\r\n");
        }
        return result;
    }

    public static String replaceBreak(String source) {
        if (source != null) {
            return source.replace("\\r", "\r").replace("\\n", "\n");
        }
        return source;
    }

    public static String getVideoHms(Integer iSeconds) {
        int seconds = 0;
        if (iSeconds != null)
            seconds = iSeconds.intValue();
        StringBuffer buf = new StringBuffer();
        int startHours = seconds / 3600;
        int startMinutes = (seconds % 3600) / 60;
        int startSeconds = (seconds % 3600) % 60;
        if (startHours > 9) {
            buf.append(startHours).append(":");
        } else if (startHours > 0) {
            buf.append("0").append(startHours).append(":");
        }
        if (startMinutes > 9) {
            buf.append(startMinutes).append(":");
        } else {
            buf.append("0").append(startMinutes).append(":");
        }
        if (startSeconds > 9) {
            buf.append(startSeconds);
        } else {
            buf.append("0").append(startSeconds);
        }
        return buf.toString();
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(getVideoHms(0));
    }
}
