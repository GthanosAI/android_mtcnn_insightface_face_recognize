package cn.com.earth.tools.time;

/**
 * 介绍: ${描述}
 * 作者: jacky
 * 邮箱: none
 * 时间:  17/5/10 下午8:29
 */

public interface IDateTime {
    long A_DAY_MS = 24 * 3600 * 1000L;
    int A_WEEK_MS = 6 * 24 * 3600 * 1000;
    int FIVE_MIN = 5 * 60 * 1000;

    int ONE_MIN = 1 * 60 * 1000;


    String YEAR_FORMAT = "yyyy-MM-dd";

    String YEAR_MONTH_FORMAT_NORMAL = "yyyy年MM月dd日";
    String YEAR_MONTH_FORMAT_TYPE1 = "yyyy-MM-dd";
    String YEAR_MONTH_FORMAT_TYPE2 = "yyyy-MM";
    String YEAR_MONTH_FORMAT_TYPE3 = "yyyy年MM月";
    String MONTH_DAY_FORMAT_TYPE1 = "MM-dd";
    String HOUR_MIN_FORMAT_NORMAL = "HH时mm分";
    String HOUR_MIN_FORMAT_TYPE1 = "HH:mm";
    String YEAR_MONTH_DATA_HOUR_MIN_SECOND = "yyyy-MM-dd HH:mm:ss";
    String C_YEAR_MONTH_DATA_HOUR_MIN_SECOND = "yyyy年MM月dd日 HH:mm:ss";
    String YEAR_MONTH_DATA_HOUR_MIN = "yyyy-MM-dd HH:mm";
    String MONTH_DATA_HOUR_MIN = "MM月dd日 HH:mm";
    String MONTH_DATA_HOUR_MIN_CHINESE = "MM月dd日 HH:mm";

    String TODAY = "今天";
    String YESTERDAY = "昨天";
    String TOMORROW = "明天";
    String BEFORE_YESTERDAY = "前天";
    String AFTER_TOMORROW = "后天";
    String SUNDAY = "星期日";
    String MONDAY = "星期一";
    String TUESDAY = "星期二";
    String WEDNESDAY = "星期三";
    String THURSDAY = "星期四";
    String FRIDAY = "星期五";
    String SATURDAY = "星期六";
}
