package com.yonyou.beisendemo.utils;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author xuweijun
 *
 */
public class DateUtil {

    private static final String dateFormatSimple = "yyyy-MM-dd";
    private static final String timeStampFormatSimple = "yyyy-MM-dd HH:mm:ss";
    private static final SimpleDateFormat dfYYYYMMdd = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateTimeFormatter yyyyMMddFmt = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter SIMPLE_DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter SIMPLE_DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 将时间按格式输出成String
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateFormat(Date date, String pattern) {
        String str = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        try {
            sdf.applyPattern(pattern);
            str = sdf.format(date);
            return str;
        }catch(Exception e) {
            return null;
        }
    }


    /**
     * 时间格斯化
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateFormat(Object date, String pattern) {
        String str = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        try {
            sdf.applyPattern(pattern);
            str = sdf.format(date);
            return str;
        }catch(Exception e) {
            return null;
        }
    }

    /**
     * 时间戳长格式 yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     * @ 
     */
    public static String getFullDateFormat(Date date)  {
        return getDateFormat(date, timeStampFormatSimple);
    }

    /**
     * 时间格式yyyy-MM-dd
     * @param date
     * @return
     * @ 
     */
    public static String getSimpleDateFormat(Date date)  {
        return getDateFormat(date, dateFormatSimple);
    }

    /**
     * Excel导入的Numeric格式时间转换为Date
     * @param days
     * @return
     */
    public static Date getExcelDate(int days) {
        Calendar c = Calendar.getInstance();
        c.set(1900, 0, 1);
        c.add(Calendar.DATE, days - 2);
        return c.getTime();
    }

    /**
     * 返回当前日期
     * @param pattern 日期格式
     * @return
     * @ 
     */
    public static String getCurrentDateFormat(String pattern)  {
        Calendar cal = Calendar.getInstance();
        return getDateFormat(cal.getTime(),pattern);
    }

    /**
     * 返回档期日期 yyyy-MM-dd
     * @return
     * @ 
     */
    public static String getCurrentDateFormat() {
        return getCurrentDateFormat(dateFormatSimple);
    }

    public static String getCurrentTimeStampFormat() {
        return getCurrentDateFormat(timeStampFormatSimple);
    }

    public static int workDays(String strStartDate, String strEndDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cl1 = Calendar.getInstance();
        Calendar cl2 = Calendar.getInstance();

        try {
            cl1.setTime(df.parse(strStartDate));
            cl2.setTime(df.parse(strEndDate));

        } catch (ParseException e) {
            System.out.println("日期格式非法");
            e.printStackTrace();
        }

        int count = 0;
        while (cl1.compareTo(cl2) <= 0) {
            if (cl1.get(Calendar.DAY_OF_WEEK) != 7 && cl1.get(Calendar.DAY_OF_WEEK) != 1)
                count++;
            cl1.add(Calendar.DAY_OF_MONTH, 1);
        }
        return count-1;
    }

    /**
     * 获取指定日期当月的第一天
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static String getFirstDayOfGivenMonth(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, 0);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定日期下个月的第一天
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static String getFirstDayOfNextMonth(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.MONTH, 1);
            return sdf.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定日期的开始时间
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static String getMonthStartTime(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 0);
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return toFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取指定日期的结束时间
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static String getMonthEndTime(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        SimpleDateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, 0);
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));// 获取当前月最后一天
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            return toFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取指定日期的开始时间
     * @return
     */
    public static Date getDateStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 获取指定日期的结束时间
     * @return
     */
    public static Date getDateEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }


    //返回当前时间的“yyyy-MM-dd”格式字符串
    public static String dataFormat(){
        Date date = new Date();//获取当前的日期
        return dfYYYYMMdd.format(date);
    }

    // 获取当前时间
    public static String getNowDateTime(DateTimeFormatter... dateTimeFormatter) {
        if (dateTimeFormatter != null && dateTimeFormatter.length > 0) {
            return dateTimeFormatter[0].format(LocalDateTime.now());
        }
        return SIMPLE_DTF.format(LocalDateTime.now());
    }

    // 获取当前日期
    public static String getNowDate(DateTimeFormatter... dateTimeFormatter) {
        if (dateTimeFormatter != null && dateTimeFormatter.length > 0) {
            return dateTimeFormatter[0].format(LocalDateTime.now());
        }
        return SIMPLE_DF.format(LocalDateTime.now());
    }

    /**
     * 判断字符串日期是否大于等于当前日期
     *
     * @param dateStr 提供的字符串日期
     * @param pattern 日期格式，例如 "yyyy-MM-dd"
     * @return true 如果字符串日期大于等于当前日期；否则 false
     */
    public static boolean isAfterOrEqualToToday(String dateStr, String pattern) {
        try {
            // 格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            // 转换字符串为 LocalDate
            LocalDate inputDate = LocalDate.parse(dateStr, formatter);
            // 获取当前日期
            LocalDate today = LocalDate.now();
            // 判断是否大于等于当前日期
            return !inputDate.isBefore(today); // 等价于 inputDate >= today
        } catch (DateTimeParseException e) {
            // 如果解析失败，抛出非法参数异常
            throw new IllegalArgumentException("Invalid date format: " + dateStr, e);
        }
    }

    /**
     * 判断字符串日期是否大于当前日期
     *
     * @param dateStr 提供的字符串日期
     * @param pattern 日期格式，例如 "yyyy-MM-dd"
     * @return true 如果字符串日期大于等于当前日期；否则 false
     */
    public static boolean isAfterToToday(String dateStr, String pattern) {
        try {
            // 格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            // 转换字符串为 LocalDate
            LocalDate inputDate = LocalDate.parse(dateStr, formatter);
            // 获取当前日期
            LocalDate today = LocalDate.now();
            // 判断是否大于等于当前日期
            return inputDate.isAfter(today); // 等价于 inputDate >= today
        } catch (DateTimeParseException e) {
            // 如果解析失败，抛出非法参数异常
            throw new IllegalArgumentException("Invalid date format: " + dateStr, e);
        }
    }

    /**
     * 比较两个字符串日期
     *
     * @param dateStr 提供的字符串日期
     * @param dateStr2 提供的字符串日期2
     * @param pattern 日期格式，例如 "yyyy-MM-dd"
     * @return 1 如果dateStr字符串日期大于dateStr2字符串日期；0 如果dateStr字符串日期等于dateStr2字符串日期；-1 如果dateStr字符串日期小于于dateStr2字符串日期
     */
    public static int compareDate(String dateStr,String dateStr2, String pattern) {
        try {
            // 格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            // 转换字符串为 LocalDate
            LocalDate inputDate = LocalDate.parse(dateStr, formatter);
            LocalDate inputDate2 = LocalDate.parse(dateStr2, formatter);
            return inputDate.compareTo(inputDate2);
        } catch (DateTimeParseException e) {
            // 如果解析失败，抛出非法参数异常
            throw new IllegalArgumentException("Invalid date format: " + dateStr, e);
        }
    }

    /**
     * 判断字符串日期是否等于当前日期
     *
     * @param dateStr 提供的字符串日期
     * @param pattern 日期格式，例如 "yyyy-MM-dd"
     * @return true 如果字符串日期大于等于当前日期；否则 false
     */
    public static boolean isEqualToToday(String dateStr, String pattern) {
        try {
            // 格式化器
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            // 转换字符串为 LocalDate
            LocalDate inputDate = LocalDate.parse(dateStr, formatter);
            // 获取当前日期
            LocalDate today = LocalDate.now();
            // 判断是否大于等于当前日期
            return inputDate.isEqual(today); // 等价于 inputDate >= today
        } catch (DateTimeParseException e) {
            // 如果解析失败，抛出非法参数异常
            throw new IllegalArgumentException("Invalid date format: " + dateStr, e);
        }
    }

}
