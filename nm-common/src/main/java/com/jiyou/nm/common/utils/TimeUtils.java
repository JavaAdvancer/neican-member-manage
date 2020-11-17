package com.jiyou.nm.common.utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 时间操作
 */
@SuppressWarnings("serial")
public class TimeUtils implements Serializable {

    public final static Long DAYOFMIllSECOND = Long.valueOf((59+59*60+23*60*60)*1000);

    static String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    private static String dateFormat = "yyyy-MM-dd";

    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //jdk1.8
    public final static DateTimeFormatter YMD_LOC_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    //jdk1.8
    public final static DateTimeFormatter YMDHMS_LOC_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static interface DateFormat{

        String CSTM_LOCAl_DATE ="yyyy-MM-dd";
        String CSTM_LOCAl_DATE_TIME ="yyyy-MM-dd HH:mm:ss";
    }


    public static int[] getTimeStamps(){
        int[] timeStamp = new int[2];

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        timeStamp[0] = getTimeStampByDate(start);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);
        Date end = calendar.getTime();
        timeStamp[1] = getTimeStampByDate(end);

        return timeStamp;
    }

    public static int getTimeStampToday(int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        return getTimeStampByDate(start);
    }

    public static int getTimeStampsMonthAgo(){
        int timeStamp;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date start = calendar.getTime();
        timeStamp = getTimeStampByDate(start);
        System.out.println("timeStamp - " + timeStamp);
        return timeStamp;
    }

    /**
     * 字符串转换成星期
     *
     * @param date
     * @return date
     */
    public static String getWeekOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekday = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekday < 0) {
            weekday = 0;
        }
        return weekDays[weekday];
    }

    /**
     * 获取系统当前时间
     *
     * @param format 时间格式 如 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);// 设置日期格式
        return df.format(new Date());// new Date()为获取当前系统时间
    }

    /**
     * 获取系统当前时间
     *
     * @param
     * @return
     */
    public static String getPreMonth() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        return df.format(c.getTime());
    }

    /**
     * 获取上个月最后一刻的时间戳
     *
     * @param
     * @return
     */
    public static int getPreMonthLastTimeStamp() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,0);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return getTimeStampByDate(c.getTime());
    }


    /**
     * 根据字符串获取时间
     *
     * @param strDate 字符串时间
     * @param format 时间格式
     * */
    public static Date strToDate(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Date strToDateZ(String strDate){
        strDate = strDate.replace("Z", " UTC");
        System.out.println(strDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date d = null;
        try {
            d = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(d);
        return d;
    }

    /**
     * 获取当前周的开始日期和结束日期（时间戳）
     * @param type 1->10位时间戳 2->13位时间戳
     * @return 开始时间 结束时间 时间段
     */
    public static TimeDataBody getCurWeekStartAndEndTimestamp(int type){
        TimeDataBody timeDataBody = new TimeDataBody();
        LocalDate now = LocalDate.now();
        DayOfWeek dayOfWeek   = now.getDayOfWeek();
        LocalDate startDate = now.plusDays(-(dayOfWeek.getValue()-1));
        LocalDate endDate = now.plusDays(7 - dayOfWeek.getValue());
        Long startTime = startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long endTime = endDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+DAYOFMIllSECOND;
        if(type==1){
            startTime = startTime/1000;
            endTime = endTime/1000;
        }
        List<String> times = new ArrayList<>();
        for(LocalDate i = startDate;(i.isBefore(endDate)||i.equals(endDate));i = i.plusDays(1)){
            times.add(i.format(YMD_LOC_FORMATTER));
        }
        timeDataBody.startTime = startTime;
        timeDataBody.endTime = endTime;
        timeDataBody.startTimeStr = startDate.toString();
        timeDataBody.endTimeStr = endDate.toString();
        timeDataBody.times = times;
        return timeDataBody;
    }
    /**
     * 获取当前月的开始日期和结束日期（时间戳）
     * @param type 1->10位时间戳 2->13位时间戳
     * @return 开始时间 结束时间 时间段
     */
    public static TimeDataBody getCurMonthStartAndEndTimestamp(int type){
        TimeDataBody timeDataBody = new TimeDataBody();
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.getDayOfMonth());
        LocalDate startDate = localDate.minusDays(localDate.getDayOfMonth() - 1);
        LocalDate endDate = localDate.plusDays(localDate.lengthOfMonth()-localDate.getDayOfMonth());
        Long startTime = startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long endTime = endDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+DAYOFMIllSECOND;
        if(type==1){
            startTime = startTime/1000;
            endTime = endTime/1000;
        }
        List<String> times = new ArrayList<>();
        for(LocalDate i = startDate;(i.isBefore(endDate)||i.equals(endDate));i = i.plusDays(1)){
            times.add(i.format(YMD_LOC_FORMATTER));
        }
        timeDataBody.startTime = startTime;
        timeDataBody.endTime = endTime;
        timeDataBody.times = times;
        return timeDataBody;
    }
    /**
     * 获取指定开始日期和结束日期（yyyy-MM-dd字符）的之间的时间段
     * @param startTimeStr
     * @param endTimeStr
     * @param type 返回时间戳的格式 1->10位时间戳 2->13位时间戳
     * @return 开始时间 结束时间 时间段
     */
    public static TimeDataBody getStartAndEndTimes(String startTimeStr,String endTimeStr,int type){
        TimeDataBody timeDataBody = new TimeDataBody();
        LocalDate startDate = LocalDate.parse(startTimeStr,YMD_LOC_FORMATTER);
        LocalDate endDate =  LocalDate.parse(endTimeStr,YMD_LOC_FORMATTER);
        Long startTime = startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long endTime = endDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+DAYOFMIllSECOND;
        if(type==1){
            startTime = startTime/1000;
            endTime = endTime/1000;
        }
        List<String> times = new ArrayList<>();
        for(LocalDate i = startDate;(i.isBefore(endDate)||i.equals(endDate));i = i.plusDays(1)){
            times.add(i.format(YMD_LOC_FORMATTER));
        }
        timeDataBody.startTime = startTime;
        timeDataBody.endTime = endTime;
        timeDataBody.startTimeStr = startTimeStr;
        timeDataBody.endTimeStr = endTimeStr;
        timeDataBody.times = times;
        return timeDataBody;
    }
    /**
     * 获取当前天的开始日期和结束日期（时间戳）
     * @param type 1->10位时间戳 2->13位时间戳
     * @return 开始时间 结束时间
     */
    public static TimeDataBody getCurDayStartAndEndTimestamp(int type){
        TimeDataBody timeDataBody = new TimeDataBody();
        LocalDate localDate = LocalDate.now();
        Long startTime = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long endTime = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+DAYOFMIllSECOND;
        if(type==1){
            startTime = startTime/1000;
            endTime = endTime/1000;
        }
        timeDataBody.startTime = startTime;
        timeDataBody.endTime = endTime;
        timeDataBody.startTimeStr = long2DateStr(startTime);
        timeDataBody.endTimeStr = long2DateStr(endTime);
        return timeDataBody;
    }
    /**
     * 获取昨天的开始日期和结束日期（时间戳）
     * @param type 1->10位时间戳 2->13位时间戳
     * @return 开始时间 结束时间
     */
    public static TimeDataBody getYestDayStartAndEndTimestamp(int type){
        TimeDataBody timeDataBody = new TimeDataBody();
        LocalDate yestLocalDayte = LocalDate.now().minusDays(1);
        Long startTime = yestLocalDayte.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long endTime = yestLocalDayte.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()+DAYOFMIllSECOND;
        if(type==1){
            startTime = startTime/1000;
            endTime = endTime/1000;
        }
        timeDataBody.startTime = startTime;
        timeDataBody.endTime = endTime;
        return timeDataBody;
    }
    public static class TimeDataBody{

        public Long startTime;

        public Long endTime;

        public String startTimeStr;

        public String endTimeStr;

        public List<String> times;

    }

    /**
     * 时间戳转换成日期格式字符串
     * @param timeStamp 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(int timeStamp,String format) {
        if (timeStamp == 0){return "";}
        if(format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(timeStamp+"000")));
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param timeStamp 精确到秒的字符串
     * @param
     * @return
     */
    public static String timeStamp2Date(int timeStamp) {
        if (timeStamp == 0){return "";}
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(Long.valueOf(timeStamp+"000")));
    }
    /**
     * 获取当前时间戳
     *
     * */
    public static Long getCurrentTimeStamp(){

        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));

        return second;
    }

    public static int getTimeStamp(){
        return (int) (System.currentTimeMillis()/1000);
    }

    public static int getTimeStampByDate(Date date){
        return (int) (date.getTime()/1000);
    }

    /**
     * date转LocalDateTime
     * */
    public static LocalDateTime dateToLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
    /**
     * date转LocalDate
     * */
    public static LocalDate dateToLocalDate(Date date){
        return TimeUtils.dateToLocalDateTime(date).toLocalDate();
    }

    /**
     * localDateTime转date
     * */
    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 字符串类型时间转date
     * */
    public static Date formatDate(String timeStr, String format){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = LocalDateTime.parse(timeStr, df).atZone(zoneId);

        return Date.from(zdt.toInstant());
    }

    /**
     * 日期转指定类型字符串
     * */
    public static String formatTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前指定类型字符串
     * */
    public static String getCurrentFormatTime(String pattern){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Date 转 日期格式 yyyy-MM-dd 字符串
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }

    /**
     * Date 转 日期格式 yyyy-MM-dd 字符串
     * @param date
     * @return
     */
    public static String dateTime2Str(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DateFormat.CSTM_LOCAl_DATE_TIME);
        return format.format(date);
    }

    /**
     * 获取当前指定类型字符串
     * */
    public static String getminusDayFormatTime(String pattern, int interval){
        LocalDate minus = LocalDate.now().minusDays(interval);
        return minus.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 传入的日期与当前日期做比较
     *
     * 大于当前日期返回true,否则返回false
     * */
    public static boolean dateCompare(String timeStr, String format){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().isBefore(LocalDateTime.parse(timeStr, df));
    }

    /**
     * 传入的日期与当前日期做比较 (解决上面方法报异常：Unable to obtain LocalDateTime from TemporalAccessor: {})
     *
     * 大于当前日期返回true,否则返回false
     * @param timeStr
     * @return
     */
    public static boolean dateCompare2(String timeStr){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        return LocalDate.now().isBefore(LocalDate.parse(timeStr, df));
    }

    /**
     * 传入的日期与当前日期做比较
     *
     * 大于当前日期返回true,否则返回false
     * @param timeStr
     * @return
     */
    public static boolean dateCompare3(String timeStr){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.now().isBefore(LocalDate.parse(timeStr, df));
    }

    /**
     * 两个日期作比较，前者小于后者为 true，否则为false
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean dateCompareToDate(Date startDate, Date endDate){
        int flag = startDate.compareTo(endDate);
        return flag < 0 ? true: false;
    }

    public static int getCurrentTimeStampToInt() {
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        return second.intValue();
    }

    /**
     * long类型转换为时间类型
     * @param lo
     * @return
     */
    public static Date long2Date(Long lo){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return stringToDate(format.format(new Date(lo*1000L)));
    }

    /**
     * long类型转换为时间类型字符串
     * @param lo
     * @return
     */
    public static String long2DateStr(Long lo){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(lo*1000L));
    }


    /**
     * 获得当天0点0分的标准格林威治时间（秒）
     *
     * @return
     */
    public static int getCurrentDaySecond() {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.set(Calendar.HOUR, 0);
        gCalendar.set(Calendar.MINUTE, 0);
        gCalendar.set(Calendar.SECOND, 0);
        gCalendar.set(Calendar.MILLISECOND, 0);
        return (int) (gCalendar.getTimeInMillis() / 1000);
    }

    // 获得当前日期
    public static Date getCurrentDate() {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gCalendar = new GregorianCalendar();
        String dateStr = sFormat.format(gCalendar.getTime());
        Date date = null;
        try {
            date = sFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    // 获得指定数值的日期字符串表示……秒
    public static String getDateStringFromSeconds(long seconds) {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTimeInMillis(seconds * 1000);
        return sFormat.format(gCalendar.getTime());
    }

    // 获得指定数值的日期字符串全表示……秒
    public static String getDateStringAllFromSeconds(long seconds) {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTimeInMillis(seconds * 1000);
        return sFormat.format(gCalendar.getTime());
    }

    /**
     * 10位时间戳构建date 对象
     * @param seconds
     * @return
     */
    public static Date getDateObjFromSeconds10(long seconds) {
        Date date = new Date(seconds*1000);
        return date;
    }

    /**
     * 根据传入日期与格式，返回日期类型
     *
     * @return
     */
    public static Date getDateByForm(String indate, String dfromat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dfromat);
        Date date = null;
        try {
            date = sdf.parse(indate);
        } catch (ParseException e) {
            e.fillInStackTrace();
        }
        return date;

    }

    /**
     * 根据传入日期与格式，返回字符串类型
     *
     * @return
     */
    public static String getDateStringByForm(Date indate, String dfromat) {
        // 日历类
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(indate);
        SimpleDateFormat formatter = new SimpleDateFormat(dfromat);
        return formatter.format(calendar.getTime());
    }

    /**
     * 日期 YYYY-MM-DD转换为date
     *
     * @param source
     * @return
     */
    public static Date stringToDate(String source) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(source);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 日期时间 yyyy-MM-dd HH:mm:ss转换为date
     *
     * @param source
     * @return
     */
    public static Date stringToDateTime(String source) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.CSTM_LOCAl_DATE_TIME);
        Date date = null;
        try {
            date = simpleDateFormat.parse(source);
        } catch (Exception e) {
        }
        return date;
    }

    /*
     * 获取传入日期，前N天或后N天日期。
     * @return
     */
    public static Date getDateByNum(Date indate, int daynum){
        // 日历类
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(indate);
        calendar.add(Calendar.DAY_OF_MONTH, daynum);
        return calendar.getTime();
    }

    // =================================年、半年、季度、月份、星期等 日期start======================================


    /**
     * 取得月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     *
     * @param date
     * @return
     */
    public static String getLastDateOfMonth2(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return date2Str(c.getTime());
    }

    /**
     * 取得季度第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfSeason(Date date) {
        return getFirstDateOfMonth(getSeasonDate(date)[0]);
    }

    /**
     * 取得季度第一天
     *
     * @param date
     * @return
     */
    public static String getFirstDateOfSeason2(Date date) {
        return date2Str(getFirstDateOfMonth(getSeasonDate(date)[0]));
    }

    /**
     * 取得季度最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfSeason(Date date) {
        return getLastDateOfMonth(getSeasonDate(date)[2]);
    }

    /**
     * 取得季度最后一天
     *
     * @param date
     * @return
     */
    public static String getLastDateOfSeason2(Date date) {
        return date2Str(getLastDateOfMonth(getSeasonDate(date)[2]));
    }

    /**
     * 取得上下半年的最后一天
     *
     * @param date
     * @return
     */
    public static String getLastDateOfHalfYear(Date date) {
        return date2Str(getLastDateOfMonth(getLastMonthHalfYear(date)));
    }

    /**
     * 取得上下半年的第一天
     *
     * @param date
     * @return
     */
    public static String getFirstDateOfHalfYear(Date date) {
        return date2Str(getFirstDateOfMonth(getFirstMonthHalfYear(date)));
    }

    /**
     * 取得季度月
     *
     * @param date
     * @return
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason == 1) {// 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {// 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {// 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {// 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 取得上半年年底月，下半年年底月
     *
     * @param date
     * @return
     */
    public static Date getLastMonthHalfYear(Date date) {
        Date season = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason < 3) {
            c.set(Calendar.MONTH, Calendar.JUNE);
            season = c.getTime();
        } else {
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season = c.getTime();
        }
        return season;
    }

    /**
     * 取得上半年年初月，下半年年初月
     *
     * @param date
     * @return
     */
    public static Date getFirstMonthHalfYear(Date date) {
        Date season = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason < 3) {
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season = c.getTime();
        } else {
            c.set(Calendar.MONTH, Calendar.JULY);
            season = c.getTime();
        }
        return season;
    }

    /**
     *
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     */
    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 取得日期：月
     *
     * @param date
     * @return
     */
    public static String getMonth(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, num);
        return date2Str(c.getTime());
    }

    /**
     * 取得时间：分钟
     *
     * @param date
     * @return
     */
    public static String getMinute(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, num);
        SimpleDateFormat format = new SimpleDateFormat(DateFormat.CSTM_LOCAl_DATE_TIME);
        return format.format(c.getTime());
    }

    /**
     * 取得日期：年
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        return year;
    }

    /**
      * 获取当年的第一天
      * @param year
      * @return
      */
    public static String getFirstDateOfYear(){
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return date2Str(getYearFirst(currentYear));
    }

    /**
      * 获取当年的最后一天
      * @param year
      * @return
      */
    public static Date getLastDateOfYear(){
        Calendar currCal = Calendar.getInstance();
        int currentYear = currCal.get(Calendar.YEAR);
        return getYearLast(currentYear);
    }

    /**
      * 获取某年第一天日期
      * @param year 年份
      * @return Date
      */
    public static Date getYearFirst(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, 0);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
      * 获取某年最后一天日期
      * @param year 年份
      * @return Date
      */
    public static Date getYearLast(int year){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    /**
      * 获取某年最后一天日期
      * @param year 年份
      * @return Date
      */
    public static String getYearLast(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, getYear(date));
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return date2Str(currYearLast);
    }

    /**
      * 获取第二年最后一天日期
      * @param year 年份
      * @return Date
      */
    public static String getNextYearLast(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, getYear(date)+1);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date currYearLast = calendar.getTime();
        return date2Str(currYearLast);
    }

    /**
     * 获取 传入日期所在年份 拼接上日月字符串
     * @param date 任意
     * @param str 10/15
     * @return  格式为：2019/10/15
     */
    public static String getYearAddMonthDay(Date date, String str) {
        return getYear(date)+"/"+str;
    }

    /**
     * LocalDateTime 转 util date
     * @param localDateTime
     * @return
     */
    public static Date getUtilDateOfLocalDateTime(LocalDateTime localDateTime){
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * LocalDateTime 转 util date
     * @param date
     * @return
     */
    public static LocalDateTime getLocalDateTimeOfUtilDate(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

	public static Date getEndOfDay(Date date) {
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(date);
		calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
		calendarEnd.set(Calendar.MINUTE, 59);
		calendarEnd.set(Calendar.SECOND, 59);
		calendarEnd.set(Calendar.MILLISECOND, 0);
		return calendarEnd.getTime();

	}

    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());

    }

// ======================================季度、月份、星期等 日期end======================================

    public static void main(String[] args) {


        System.out.println("当前月第一天：" + date2Str(getFirstDateOfMonth(new Date())));
        System.out.println("当前是第几季度: " + getSeason(new Date()));
        System.out.println("当前季度第一天: " + date2Str(getFirstDateOfSeason(new Date())));
        System.out.println("当前季度最后一天: " + date2Str(getLastDateOfSeason(new Date())));

        System.out.println("上半年还是下半年的最后一天：" + getLastDateOfHalfYear(new Date()));
        System.out.println("上半年还是下半年的第一天：" + getFirstDateOfHalfYear(new Date()));
        System.out.println("当某年的第一天："+ getFirstDateOfYear());
        System.out.println("当某年的最后一天："+ getYearLast(new Date()));

        System.out.println("当前月份："+ getMonth(new Date(), -3));

        System.out.println("日期：" + date2Str(long2Date(1566996774L)));

        System.out.println("long: "+ stringToDate("2019-06-10").getTime()/1000);

        System.out.println("当前时间："+ getMinute(new Date(), 3));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd");
        System.out.println("当前日期格式： "+ simpleDateFormat.format(new Date()));

        System.out.println("添加：" + getYearAddMonthDay(new Date(), "10/12"));

        System.out.println("添加：" + dateCompare3(getYearAddMonthDay(new Date(), "10/12")));

        Date dateByNum = getDateByNum(new Date(), -2);
        System.out.println(dateByNum);

        TimeDataBody dataBody = getCurDayStartAndEndTimestamp(1);
        System.out.println(dataBody);


    }


}
