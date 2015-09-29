package com.tdb.share.utils;

import java.util.Date;

/**
 * Created by Administrator on 2015/9/29.
 */
public class DisplayTimeUtils {
//    public static String getBriefTime(Date time) {
//        Calendar calendarStandard = Calendar.getInstance();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(time);
//        if (calendar.get(Calendar.YEAR) == calendarStandard.get(Calendar.YEAR)
//                && calendar.get(Calendar.MONTH) == calendarStandard.get(Calendar.MONTH)
//                && calendar.get(Calendar.DAY_OF_MONTH) == calendarStandard.get(Calendar.DAY_OF_MONTH)) {
//            String prefixString;
//            if (calendar.get(Calendar.HOUR_OF_DAY) <= 10) {
//                prefixString = "早上";
//            } else if (calendar.get(Calendar.HOUR_OF_DAY) <= 13) {
//                prefixString = "中午";
//            } else if (calendar.get(Calendar.HOUR_OF_DAY) <= 17) {
//                prefixString = "下午";
//            } else {
//                prefixString = "晚上";
//            }
//            return prefixString + DateHelper.dateTimetoStringTime(time);
//        } else if (isYesterday(time)) {
//            return "昨天" + DateHelper.dateTimetoStringTime(time);
//        } else if (isTheDayBeforeYesterday(time)) {
//            return "前天" + DateHelper.dateTimetoStringTime(time);
//        } else if (calendar.get(Calendar.YEAR) == calendarStandard.get(Calendar.YEAR)) {
//            return DateHelper.dateTimetoStringDateTime(time);
//        } else {
//            return DateHelper.dateTimeToStringCN(time);
//        }
//    }
//
//    public static String getActInfoEndTime(Date time) {
//        if (time.getTime() < (new Date()).getTime()) {
//            return null;
//        }
//        Calendar calendarStandard = Calendar.getInstance();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(time);
//        if (calendar.get(Calendar.YEAR) == calendarStandard.get(Calendar.YEAR)
//                && calendar.get(Calendar.MONTH) == calendarStandard.get(Calendar.MONTH)
//                && calendar.get(Calendar.DAY_OF_MONTH) == calendarStandard.get(Calendar.DAY_OF_MONTH)) {
//            String prefixString;
//            if (calendar.get(Calendar.HOUR_OF_DAY) <= 10) {
//                prefixString = "早上";
//            } else if (calendar.get(Calendar.HOUR_OF_DAY) <= 13) {
//                prefixString = "中午";
//            } else if (calendar.get(Calendar.HOUR_OF_DAY) <= 17) {
//                prefixString = "下午";
//            } else {
//                prefixString = "晚上";
//            }
//            return prefixString + DateHelper.dateTimetoStringTime(time);
//        } else if (isTomorrow(time)) {
//            return "明天" + DateHelper.dateTimetoStringTime(time);
//        } else if (isTheDayAfterTomorrow(time)) {
//            return "后天" + DateHelper.dateTimetoStringTime(time);
//        } else if (calendar.get(Calendar.YEAR) == calendarStandard.get(Calendar.YEAR)) {
//            return DateHelper.dateTimetoStringMonthDay(time);
//        } else {
//            return DateHelper.dateTimetoStringYearMonthDay(time);
//        }
//    }
//
//    @SuppressWarnings("deprecation")
//    public static String getRelativeTime(Date time) {
//        Date dataNow = new Date();
//        int valueNow, value;
//        valueNow = (int) (dataNow.getTime() / 1000);
//        value = (int) (time.getTime() / 1000);
//        String str;
//        if (valueNow - value < 180) {
//            return "刚刚";
//        }
//        if (valueNow - value < 3660) {
//            int temp = (valueNow - value) / 60;
//            str = temp + "分钟前";
//            return str;
//        }
//        if (valueNow - value < 21600) {
//            int temp = (valueNow - value) / 3600;
//            str = temp + "小时前";
//            return str;
//        }
//        if ((time.getYear() == dataNow.getYear()) && (time.getMonth() == dataNow.getMonth()) && (time.getDay() == dataNow.getDay())) {
//            str = "今天 " + DateHelper.dateTimetoStringTime(time);
//            return str;
//        }
//        if (isYesterday(time)) {
//            str = "昨天 " + DateHelper.dateTimetoStringTime(time);
//            return str;
//        }
//        Calendar calendarStandard = Calendar.getInstance();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(time);
//        if (calendar.get(Calendar.YEAR) == calendarStandard.get(Calendar.YEAR)) {
//            return DateHelper.dateTimetoStringDateTime(time);
//        } else {
//            return DateHelper.dateTimeToStringCN(time);
//        }
//    }

    @SuppressWarnings("deprecation")
    public static boolean isTomorrow(Date time) {
        Date dataNow = new Date();
        int valueNow, value;
        valueNow = (int) (dataNow.getTime() / 1000);
        value = (int) (time.getTime() / 1000);
        int dValue = value - time.getHours() * 3600 - time.getMinutes() * 60 - time.getSeconds() - valueNow;
        return dValue > 0 && dValue <= 86400;
    }

    @SuppressWarnings("deprecation")
    public static boolean isTheDayAfterTomorrow(Date time) {
        Date dataNow = new Date();
        int valueNow, value;
        valueNow = (int) (dataNow.getTime() / 1000);
        value = (int) (time.getTime() / 1000);
        int dValue = value - time.getHours() * 3600 - time.getMinutes() * 60 - time.getSeconds() - valueNow;
        return dValue > 86400 && dValue <= 86400 * 2;
    }

    @SuppressWarnings("deprecation")
    public static boolean isYesterday(Date time) {
        Date dataNow = new Date();
        int valueNow, value;
        valueNow = (int) (dataNow.getTime() / 1000);
        value = (int) (time.getTime() / 1000);
        int dValue = valueNow - dataNow.getHours() * 3600 - dataNow.getMinutes() * 60 - dataNow.getSeconds() - value;
        return dValue > 0 && dValue <= 86400;
    }

    @SuppressWarnings("deprecation")
    public static boolean isTheDayBeforeYesterday(Date time) {
        Date dataNow = new Date();
        int valueNow, value;
        valueNow = (int) (dataNow.getTime() / 1000);
        value = (int) (time.getTime() / 1000);
        int dValue = valueNow - dataNow.getHours() * 3600 - dataNow.getMinutes() * 60 - dataNow.getSeconds() - value;
        return dValue > 86400 && dValue <= 86400 * 2;
    }
}
