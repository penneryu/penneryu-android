package com.penner.android.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by PennerYu on 15/11/4.
 */
public final class DateUtils {

    public static String getTimestampString(Date date)
    {
        String str;
        long time = date.getTime();
        if (isSameDay(time)) {
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 17)
                str = "晚上 hh:mm";
            else if ((hour >= 0) && (hour <= 6))
                str = "凌晨 hh:mm";
            else if ((hour > 11) && (hour <= 17))
                str = "下午 hh:mm";
            else
                str = "上午 hh:mm";
        }
        else if (isYesterday(time)) {
            str = "昨天 HH:mm";
        }
        else {
            str = "M月d日 HH:mm";
        }
        return new SimpleDateFormat(str, Locale.CHINA).format(date);
    }

    public static boolean isShowMessageTime(long newTime, long oldTime) {
        long time = newTime - oldTime;
        return time > 60000;
    }

    private static boolean isSameDay(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);

        long start = calendar.getTime().getTime();
        long end = calendar2.getTime().getTime();

        return time > start && time < end;
    }

    private static boolean isYesterday(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DAY_OF_MONTH, -1);
        calendar2.set(Calendar.HOUR_OF_DAY, 23);
        calendar2.set(Calendar.MINUTE, 59);
        calendar2.set(Calendar.SECOND, 59);
        calendar2.set(Calendar.MILLISECOND, 999);

        long start = calendar.getTime().getTime();
        long end = calendar2.getTime().getTime();

        return time > start && time < end;
    }
}
