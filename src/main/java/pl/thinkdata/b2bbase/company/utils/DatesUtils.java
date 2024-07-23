package pl.thinkdata.b2bbase.company.utils;

import java.util.Calendar;
import java.util.Date;

public class DatesUtils {

    public static Date addYearToTime(Date date, int year) {
        Calendar nowPlusOneYear = Calendar.getInstance();
        nowPlusOneYear.setTime(date);
        nowPlusOneYear.add(Calendar.YEAR, year);
        return nowPlusOneYear.getTime();
    }
}
