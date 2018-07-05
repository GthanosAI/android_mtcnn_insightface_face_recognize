package cn.com.earth.tools.time;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class TimeUtils implements IDateTime {
    SimpleDateFormat dayFormat = null;
    SimpleDateFormat dateFormat = null;
    static SimpleDateFormat yearFormat = null;
    static Calendar msgCalendar = null;

    public static String getMothData(Long time) {
        Long cuurentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
        String currentMonth = sdf.format(new Date(cuurentTime));
        String month = sdf.format(new Date(time));
        if (month.equals(currentMonth)) {
            return "本月";
        } else {
            return month;
        }
    }

    /**
     * 时间戳转换成字符串
     *
     * @param time
     * @param format 转换格式
     * @return
     */
    public static String getStrDate(Long time, String format) {
        String tmpTime = String.valueOf(time);
        if (tmpTime.length() == 10) {
            time = time * 1000;
        }

        String ret = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        ret = sdf.format(new Date(time));

        return ret;
    }

    // time format covert
    public static String getStrTime(Long time) {
        String ret = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH时mm分");
        ret = sdf.format(new Date(time));

        return ret;
    }

    public static String getStrDate(Long time) {
        String ret = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        ret = sdf.format(new Date(time));

        return ret;
    }


    public static String getRealtime(Long time) {
        if (null == time) {
            time = 0L;
        }
        if (msgCalendar == null)
            msgCalendar = Calendar.getInstance();

        msgCalendar.setTimeInMillis(time);

        if (yearFormat == null)
            yearFormat = new SimpleDateFormat(YEAR_FORMAT);
        String result = yearFormat.format(msgCalendar.getTime());
        return new StringBuilder().append(result).toString();

    }

    private static boolean isSameHalfDay(Calendar now, Calendar msg) {
        int nowHour = now.get(Calendar.HOUR_OF_DAY);
        int msgHOur = msg.get(Calendar.HOUR_OF_DAY);

        if (nowHour <= 12 & msgHOur <= 12) {
            return true;
        } else if (nowHour >= 12 & msgHOur >= 12) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isSameDay(Calendar now, Calendar msg) {
        int nowDay = now.get(Calendar.DAY_OF_YEAR);
        int msgDay = msg.get(Calendar.DAY_OF_YEAR);

        return nowDay == msgDay;
    }

    private static boolean isYesterDay(Calendar now, Calendar msg) {
        int nowDay = now.get(Calendar.DAY_OF_YEAR);
        int msgDay = msg.get(Calendar.DAY_OF_YEAR);

        return (nowDay - msgDay) == 1;
    }

    private static boolean isTheDayBeforeYesterDay(Calendar now, Calendar msg) {
        int nowDay = now.get(Calendar.DAY_OF_YEAR);
        int msgDay = msg.get(Calendar.DAY_OF_YEAR);

        return (nowDay - msgDay) == 2;
    }

    private static boolean isSameYear(Calendar now, Calendar msg) {
        int nowYear = now.get(Calendar.YEAR);
        int msgYear = msg.get(Calendar.YEAR);

        return nowYear == msgYear;
    }

    public static String getTime(String str) {
        if (TextUtils.isEmpty(str)) return "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date(Long.parseLong(str)));
    }


    public static Date string2Date(String dateString, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 前面的日期是否在后面的日期后面
     *
     * @return
     */
    public static boolean isDateAfterDate(int year1, int month1, int day1, int year2, int month2, int day2) {
        if (year1 > year2) {
            return true;
        }
        if (month1 > month2) {
            return true;
        }
        if (day1 > day2) {
            return true;
        }

        return false;
    }

    /**
     * 前面的日期是否在后面的日期前面
     *
     * @return
     */
    public static boolean isDateBeforeDate(int year1, int month1, int day1, int year2, int month2, int day2) {
        if (year1 < year2) {
            return true;
        }
        if (month1 < month2) {
            return true;
        }
        if (day1 < day2) {
            return true;
        }

        return false;
    }

    public static String getDate(String str) {
        if (TextUtils.isEmpty(str)) return "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        return formatter.format(new Date(Long.parseLong(str) * 1000));
    }

    public static String getDateJava(String str) {
        if (TextUtils.isEmpty(str)) return "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        return formatter.format(new Date(Long.parseLong(str)));
    }


    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @return
     */
    public static String date2TimeStamp(String date_str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_FORMAT_TYPE2);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 日期比较
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareDate(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.before(dt2)) {
                // System.out.println("dt1在dt2前");
                return true;
            } else if (dt1.after(dt2)) {
                // System.out.println("dt1在dt2后");
                return false;
            } else {
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }


    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000 * SECONDS_IN_DAY;

    /**
     * 是否同一天
     *
     * @param s1
     * @param s2
     * @return
     */
    public static boolean isSameDayByMs(final long s1, final long s2) {
        final long interval = s1 - s2;
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(s1) == toDay(s2);
    }


    public static boolean isSameYear(long time1, long time2) {
        if (msgCalendar == null)
            msgCalendar = Calendar.getInstance();
        int year1 = 0;
        int year2 = 0;
        msgCalendar.setTimeInMillis(time1);
        year1 = msgCalendar.get(Calendar.YEAR);
        msgCalendar.setTimeInMillis(time2);
        year2 = msgCalendar.get(Calendar.YEAR);
        return year1 == year2;

    }

    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
    }

    /**
     * 获取最近三天的时间字符，如果为空则用其他规则获取(个人中心使用)
     *
     * @param ms
     * @return
     */
    public static String getThreenDay(long ms) {
        int type = getDayStyle(ms);
        if (1 == type) {
            return "今天";
        } else if (2 == type) {
            return "昨天";
        } else if (3 == type) {
            return "前天";
        }
        return null;
    }

    /**
     * 是否今天
     *
     * @param ms
     * @return
     */
    public static boolean isToday(long ms) {
        return isSameDayByMs(ms, Calendar.getInstance().getTime().getTime());
    }

    /**
     * 是否昨天
     *
     * @param ms
     * @return
     */
    public static boolean isLastDay(long ms) {
        return 2 == getDayStyle(ms);
    }

    /**
     * 是否前天
     *
     * @param ms
     * @return
     */
    public static boolean isLastLastDay(long ms) {
        return 3 == getDayStyle(ms);
    }

    private static int getDayStyle(long ms) {
        Date date = new Date(ms);  //转换为妙
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(now);
        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);

        if (dateCalendar.after(todayCalendar)) {// 判断是不是今天
            return 1;
        } else {
            todayCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是昨天
                return 2;
            }
            todayCalendar.add(Calendar.DATE, -2);
            if (dateCalendar.after(todayCalendar)) {// 判断是不是前天
                return 3;
            }
        }
        return -1;
    }

    /**
     * 是否是今年
     *
     * @param ms
     * @return
     */
    public static boolean isThisYear(long ms) {
        Date today = Calendar.getInstance().getTime();
        Date date = new Date(ms);
        return today.getYear() == date.getYear();
    }


    /**
     * 个人中心列表上时间
     *
     * @return
     */
    public static String getUcTime(long ms, SimpleDateFormat sdf) {
        if (null != sdf) {
            return sdf.format(new Date(ms));
        }
        return null;
    }


    public static String getActivTime(long startTime, long endTime) {
        return getStrDate(startTime, MONTH_DATA_HOUR_MIN_CHINESE) + " - " + getStrDate(endTime, MONTH_DATA_HOUR_MIN_CHINESE);
    }

    public static String getMD(long time) {
        return getStrDate(time, MONTH_DAY_FORMAT_TYPE1);
    }

    public static String getYYYY(long time) {
        return getStrDate(time, "yyyy");
    }

    public static String getMdotD(long time) {
        return getStrDate(time, "MM.dd");
    }


    private static SimpleDateFormat hourSdf = new SimpleDateFormat(HOUR_MIN_FORMAT_TYPE1);

    public static String getHourTime(long t) {
        if (t > 0) {
            return hourSdf.format(new Date(t));
        }
        return "";
    }


    //判断选择的日期是否是本月
    public static boolean isThisMonth(long time) {
        return isThisTime(System.currentTimeMillis(), time, YEAR_MONTH_FORMAT_TYPE2);
    }

    //是否在同一月
    public static boolean isSameMonth(long t1, long t2) {
        return isThisTime(t1, t2, YEAR_MONTH_FORMAT_TYPE2);
    }

    //生活轨迹月份时间
    public static String getLifeTackTime1(long t) {
        Date date = new Date(t);
        SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_FORMAT_TYPE3);
        return sdf.format(date);
    }

    //生活轨迹
    private static SimpleDateFormat LIFE_SDF1 = new SimpleDateFormat("MM.dd\nHH:mm");
    private static SimpleDateFormat LIFE_SDF2 = new SimpleDateFormat("HH:mm");

    //生活轨迹具体时间
    public static String getLifeTrackTime2(long t) {
        if (isToday(t)) {          //今天
            return "今天  " + LIFE_SDF2.format(new Date(t));
        } else if (isLastDay(t)) {  //昨天
            return "昨天  " + LIFE_SDF2.format(new Date(t));
        } else if (isLastLastDay(t)) {//前天
            return "前天  " + LIFE_SDF2.format(new Date(t));
        } else {
            return LIFE_SDF1.format(new Date(t));
        }
    }

    private static boolean isThisTime(long t1, long t2, String pattern) {
        Date date = new Date(t1);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);
        String now = sdf.format(new Date(t2));
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    /**
     * 相册时间转换
     *
     * @param date 2006-08-09
     **/
    public static long albumDate2Long(String date) {
        if (!TextUtils.isEmpty(date)) {
            SimpleDateFormat sdf = new SimpleDateFormat(YEAR_MONTH_FORMAT_TYPE1);
            try {
                Date dt2 = sdf.parse(date);
                return dt2.getTime();
            } catch (Exception e) {
                return 0L;
            }
        } else {
            return 0L;
        }
    }


    /**
     * 得到两个日期相差的天数
     */
    public static int getBetweenDay(Date date1, Date date2) {
        Calendar d1 = new GregorianCalendar();
        d1.setTime(date1);
        Calendar d2 = new GregorianCalendar();
        d2.setTime(date2);
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }


    public static boolean isNowIn(String startTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        try {
            calendar1.setTime(sdf.parse(startTime));
            calendar1.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            long start = calendar1.getTimeInMillis();

            calendar1.setTime(sdf.parse(endTime));
            calendar1.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            long end = calendar1.getTimeInMillis();

            long time = System.currentTimeMillis();
            return time > start && time < end;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getListTimeStr(long time) {
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - time;
        if (deltaTime <= ONE_MIN) {
            return "刚刚";
        } else if (deltaTime < FIVE_MIN) {
            return "5分钟前";
        } else {
            return getStrDate(time, HOUR_MIN_FORMAT_TYPE1);
        }
    }

    /**
     * xxxx-xx-xx
     *
     * @param formatYearMonth
     * @return
     */
    public static String getAgeStr(String formatYearMonth) {
        if (TextUtils.isEmpty(formatYearMonth)) return null;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(formatYearMonth);
            int year = date.getYear();
            if (year < 2000) {
                return (year % 100 / 10) + "0后";
            } else {
                return "00后";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getDateDetail(long time) {
        Calendar today = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        target.setTimeInMillis(time);
        today.setTimeInMillis(System.currentTimeMillis());
        long intervalMilli = target.getTimeInMillis() - today.getTimeInMillis();
        int diffDay = 0;
        if (intervalMilli < 0) {
            diffDay = -1;
        } else {
            diffDay = (int) (intervalMilli / A_DAY_MS);
        }
        return showDateDetail(diffDay, target, time);
    }

    private static String showDateDetail(int diffDay, Calendar target, long time) {
        String end = getStrDate(time, HOUR_MIN_FORMAT_TYPE1);
        if (diffDay >= 0 && diffDay < 7) {
            String head = "";
            if (diffDay == 0) {
                head = TODAY;
            } else if (diffDay == 1) {
                head = TOMORROW;

            } else if (diffDay == 2) {
                head = AFTER_TOMORROW;
            } else if (diffDay < 7) {
                int dayForWeek = 0;
                dayForWeek = target.get(Calendar.DAY_OF_WEEK);
                switch (dayForWeek) {
                    case 1:
                        head = SUNDAY;
                        break;
                    case 2:
                        head = MONDAY;
                        break;
                    case 3:
                        head = TUESDAY;
                        break;
                    case 4:
                        head = WEDNESDAY;
                        break;
                    case 5:
                        head = THURSDAY;
                        break;
                    case 6:
                        head = FRIDAY;
                        break;
                    case 7:
                        head = SATURDAY;
                        break;
                }
            }
            return head + " " + end;
        } else {
            return getStrDate(time, MONTH_DATA_HOUR_MIN);
        }
    }
}
